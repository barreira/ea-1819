package com.gestaoespacos.app.beans;

import com.gestaoespacos.app.model.*;
import com.gestaoespacos.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Scope(value= ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ResponsavelBean {

    private UtilizadorCPDRRepository ucpdr;
    private PedidoRepository pr;
    private AlteracaoRepository upr;
    private EventoRepository er;
    private HorarioRepository hr;

    @Autowired
    public ResponsavelBean(UtilizadorCPDRRepository ucpdr, PedidoRepository pr, AlteracaoRepository upr, EventoRepository er, HorarioRepository hr){
        this.ucpdr = ucpdr;
        this.er = er;
        this.pr = pr;
        this.upr = upr;
        this.hr = hr;
    }

    /**
     * O utilizador com privilegios de requisição efetua o pedido de alocação de espaço, a ser aprovado pelo gestor
     * Caso falte menos de 1 hora para o inicio do evento e este último não causar conflito com outros, será
     * automaticamente efetivado.
     * @param id_usercpdr
     * @param a
     * @return
     * @throws IdNotFoundException
     */
    public Pedido alocarEspaco(long id_usercpdr, Alocacao a) throws IdNotFoundException {
        Optional<UtilizadorCPDR> u_opt = ucpdr.findById(id_usercpdr);

        if(!u_opt.isPresent())
            throw new IdNotFoundException("UtilizadorCPDR with id="+id_usercpdr+" not found.");

        UtilizadorCPDR u = u_opt.get();

        //Se falta menos de uma hora para o possível agendamento
        if(UtilsGHE.menos1Hora(LocalDateTime.now(), a.getDateTimeInicial()) &&
                //e este não causa conflito com mais nenhum
                getConflitos(a.getEspaco(), a.getDateTimeInicial(), a.getDateTimeFinal()).size() == 0){
            //Então o evento fica efetivo
            Evento e = makeEvento(u, a);
            //Atualizar horario do espaco
            Horario h = e.getEspaco().getHorario();
            h.addEvento(e);

            a.setAceite(true);
            a.setAtendido(true);

            Notificacao n = new Notificacao("Evento criado com sucesso");
            u.addNotificacao(n);

            er.save(e);
            hr.save(h);

        }

        u.alocarEspaco(a);

        pr.save(a);
        ucpdr.save(u);

        return a;
    }

    /**
     * Responsável decidiu cancelar um pedido pedente efetuado por ele
     * @param id_usercpdr
     * @param nr_pedido
     * @return
     * @throws IdNotFoundException
     */
    public Pedido cancelarPedido(long id_usercpdr, long nr_pedido) throws IdNotFoundException{
        Optional<UtilizadorCPDR> u_opt = ucpdr.findById(id_usercpdr);

        if(!u_opt.isPresent())
            throw new IdNotFoundException("UtilizadorCPDR with id="+id_usercpdr+" not found.");

        UtilizadorCPDR u = u_opt.get();

        Optional<Pedido> p_opt = pr.findById(nr_pedido);

        if(!p_opt.isPresent())
            throw new IdNotFoundException("Pedido with id="+nr_pedido+" not found.");

        Pedido p = p_opt.get();

        if(!u.getPedidos().contains(p))
            throw new IdNotFoundException("Pedido with id="+nr_pedido+" not found for the UtilizadorCPDR with id="+id_usercpdr+".");

        u.cancelaPedido(p);

        pr.delete(p);
        ucpdr.save(u);

        return p;
    }

    /**
     * O utilizador com privilegios de requisição efetua um pedido de alteração de um evento pelo qual é responsável.
     * Se faltar menos de 1 hora para o inicio proposto deste evento, e a sua nova especificação não causar conflito
     * com outros, será automaticamente aceite. Senão, ficará à espera de atendimento por parte do gestor.
     * @param id_usercpdr
     * @param a
     * @return
     * @throws IdNotFoundException
     */
    public Pedido alterarEvento(long id_usercpdr, Alteracao a) throws IdNotFoundException{
        Optional<UtilizadorCPDR> u_opt = ucpdr.findById(id_usercpdr);

        if(!u_opt.isPresent())
            throw new IdNotFoundException("UtilizadorCPDR with id="+id_usercpdr+" not found.");

        UtilizadorCPDR u = u_opt.get();

        Evento e = a.getEvento();

        if(e.getUtilizadorResponsavel().getId() != u.getId())
            throw new IdNotFoundException("UtilizadorCPDR with id="+id_usercpdr+" is not the owner of event with id="+e.getId()+".");


        LocalDateTime inicio = a.getDateTimeInicial();
        LocalDateTime fim = a.getDateTimeFinal();

        //Se a data do evento foi alterada e falta menos de uma hora para o possível novo agendamento
        if(UtilsGHE.menos1Hora(LocalDateTime.now(), inicio) &&
                //e este não causa conflito com mais nenhum
                getConflitos(e, a.getEspaco(), inicio, fim).size() == 0) {
            //Então as alterações são aplicadas
            //Se o espaço foi alterado
            Espaco oldEsp = e.getEspaco();
            Espaco newEsp = a.getEspaco();
            Horario hAntigo = oldEsp.getHorario();
            Horario hNovo = newEsp.getHorario();
            boolean novoEspaco = !oldEsp.equals(newEsp);

            if(novoEspaco){
                //Adaptamos os horários
                hAntigo.removeEvento(e);
                hNovo.addEvento(e);
            }
            //Novas informações
            UtilsGHE.updateEvento(e, a);

            a.setAceite(true);
            a.setAtendido(true);

            //Notificar o responsável
            Notificacao n = new Notificacao("O detalhes do evento "+e.getNome()+" foram alterados. Por favor, consulte as novas informações.");
            u.addNotificacao(n);

            //Notificar seguidores
            e.getSeguidores().forEach(s -> s.addNotificacao(n));

            er.save(e);

            if(novoEspaco){
                hr.save(hAntigo);
                hr.save(hNovo);
            }
        }

        u.alterarEvento(a);

        pr.save(a);
        ucpdr.save(u);

        return a;
    }

    /**
     * O responsável pelo evento cancela o mesmo, notificando os seguidores.
     * @param id_responsavel
     * @param id_evt
     * @param justificacao
     * @return
     * @throws IdNotFoundException
     */
    public Evento cancelaEvento(long id_responsavel, long id_evt, String justificacao) throws IdNotFoundException{
        Responsavel r = ucpdr.getOne(id_responsavel);

        if(r == null)
            throw new IdNotFoundException("Responsavel with id="+id_responsavel+" not found.");

        Optional<Evento> e_opt = er.findById(id_evt);

        if(!e_opt.isPresent())
            throw new IdNotFoundException("Evento with id="+id_evt+" not found.");

        Evento e = e_opt.get();

        if(e.getUtilizadorResponsavel().getId() != id_responsavel)
            throw new IdNotFoundException("Evento with id="+id_evt+" not owned by responsável with id="+ id_responsavel + ".");

        r.cancelaEvento(e);

        Notificacao n = new Notificacao(justificacao);

        //Notificar seguidores
        e.getSeguidores().forEach(s -> {
            s.addNotificacao(n);
            s.unfollow(e);
        } );

        //Eliminar pedidos pendentes associados ao evento
        upr.deleteByEvento(e);
        er.delete(e);
        //ucpdr.save((UtilizadorCPDR)r);
        return e;
    }

    /**
     * O responsável pelo evento cancela o mesmo, notificando os seguidores.
     * Não sendo fornecida uma justificação, gera automaticamente.
     * @param id_responsavel
     * @param id_evt
     * @return
     * @throws IdNotFoundException
     */
    public Evento cancelaEvento(long id_responsavel, long id_evt) throws IdNotFoundException{
        return cancelaEvento(id_responsavel, id_evt, "O evento foi cancelado.");
    }

    /**
     * Obter os eventos pelos quais o utilizador especificado é responsável.
     * @param id_resp
     * @return
     * @throws IdNotFoundException
     */
    public List<Evento> eventosResp(long id_resp) throws IdNotFoundException{
        Responsavel r = ucpdr.getOne(id_resp);

        if(r == null)
            throw new IdNotFoundException("Responsavel with id="+id_resp+" not found.");

        return r.meusEventos();
    }

    /**
     * Determina os pedidos pendentes/atendidos efetuados por este utilizador c/ privilegios.
     * @param id_usercpdr
     * @param atendido
     * @return
     */
    public List<Pedido> getPedidosByAtendimento(long id_usercpdr, boolean atendido){
        return pr.findAllByAtendido(id_usercpdr, atendido);
    }


    /**----------------------------------------------------------------------------------------------------------------------------------------------
     * Métodos auxiliares
     * ----------------------------------------------------------------------------------------------------------------------------------------------
     */

    /**
     * Determina eventos que fazem conflito com a informação de um dado evento
     * @param esp
     * @param inicio
     * @param fim
     * @return
     */
    private List<Evento> getConflitos(Espaco esp, LocalDateTime inicio, LocalDateTime fim){
        return er.findAll().stream()
                           .filter(e -> e.getEspaco().equals(esp) &&
                                   UtilsGHE.conflitoPeriodo(inicio, fim, e.getDateTimeInicial(), e.getDateTimeFinal(), e.getPeriodicidade(), e.getLimite()))
                           .collect(Collectors.toList());
    }

    /**
     * Determina eventos que fazem conflito com a informação de um dado evento
     * @param ev
     * @param esp
     * @param inicio
     * @param fim
     * @return
     */
    private List<Evento> getConflitos(Evento ev, Espaco esp, LocalDateTime inicio, LocalDateTime fim){
        return er.findAll().stream()
                 .filter(e -> ev.getId() != e.getId() && e.getEspaco().getId() == esp.getId() &&
                       UtilsGHE.conflitoPeriodo(inicio, fim, e.getDateTimeInicial(), e.getDateTimeFinal(), e.getPeriodicidade(), e.getLimite()))
                 .collect(Collectors.toList());
    }

    /**
     * Constrói um Evento a partir da informação do pedido.
     * @param r
     * @param p
     * @return
     */
    private Evento makeEvento(UtilizadorCPDR r, Pedido p){
        Evento e =  new Evento(p.getNome(), p.getDescricao(), p.getDateTimeInicial(), p.getDateTimeFinal(), p.getPeriodicidade(), p.getLimite());
        e.setUtilizadorResponsavel(r);
        e.setEspaco(p.getEspaco());
        return e;
    }

    /**
     * Atualiza a informação de um evento
     * @param e
     * @param a
     */
    /*private void updateEvento(Evento e, Alteracao a){
        e.setNome(a.getNome());
        e.setDescricao(a.getDescricao());
        e.setDateTimeInicial(a.getDateTimeInicial());
        e.setDateTimeFinal(a.getDateTimeFinal());
        e.setEspaco(a.getEspaco());
        e.setPeriodicidade(a.getPeriodicidade());
        e.setLimite(a.getLimite());
    }*/
}
