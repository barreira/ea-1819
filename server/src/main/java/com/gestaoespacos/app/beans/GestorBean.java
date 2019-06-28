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
public class GestorBean {

    private GestorEspacos gestor;
    private GestorEspacosRepository gr;
    private AdministradorRepository ar;
    private EventoRepository er;
    private PedidoRepository pr;
    private AlocacaoRepository alr;
    private AlteracaoRepository upr;
    private EspacoComumRepository ecr;
    private NotificacaoRepository nr;
    private UtilizadorCPDRRepository ucpdr;
    private HorarioRepository hr;

    @Autowired
    public GestorBean(GestorEspacosRepository gr, AdministradorRepository ar, EventoRepository er, PedidoRepository pr,
                      AlocacaoRepository alr, AlteracaoRepository upr, EspacoComumRepository ecr,
                      NotificacaoRepository nr, UtilizadorCPDRRepository ucpdr, HorarioRepository hr){
        this.gr = gr;
        this.ar = ar;
        this.er = er;
        this.pr = pr;
        this.alr = alr;
        this.upr = upr;
        this.ecr = ecr;
        this.nr = nr;
        this.ucpdr = ucpdr;
        this.hr = hr;

        List<GestorEspacos> ges = gr.findAll();
        this.gestor = ges.size() > 0 ? ges.get(0) : null;
    }

    /**
     * Cancela o evento, notificando os respetivo responsável e os seguidores do evento.
     * @param id_evt
     * @param justificacao
     * @return
     * @throws IdNotFoundException
     */
    public Evento cancelaEvento(long id_evt, String justificacao) throws IdNotFoundException{
        Optional<Evento> e_opt = er.findById(id_evt);

        if(!e_opt.isPresent())
            throw new IdNotFoundException("Evento with id="+id_evt+" not found.");

        Evento e = e_opt.get();
        boolean respIsGestor = e.getUtilizadorResponsavel().getId() == gestor.getId();
        Notificacao n = new Notificacao(justificacao);

        if(respIsGestor)
            gestor.cancelaEvento(e);
        else{
            //Cancelar evento e notificar responsável
            UtilizadorCPDR u = (UtilizadorCPDR)e.getUtilizadorResponsavel();
            u.cancelaEvento(e);
            u.addNotificacao(n);
        }

        //Notificar seguidores
        e.getSeguidores().forEach(s -> {
            s.addNotificacao(n);
            s.unfollow(e);
        } );

        //Eliminar pedidos pendentes associados ao evento
        upr.deleteByEvento(e);

        //Eliminar eveneto
        er.delete(e);

        return e;

    }

    /**
     * Cancela o evento, notificando os respetivo responsável e os seguidores do evento.
     * Não fornece uma justificação personalizada.
     * @param id_evt
     * @return
     * @throws IdNotFoundException
     */
    public Evento cancelaEvento(long id_evt) throws IdNotFoundException{
        return cancelaEvento(id_evt, "O evento foi cancelado.");
    }

    /**
     * Obter os eventos criados pelo gestor.
     * @return
     */
    public List<Evento> eventosResp(){
        return gestor.meusEventos();
    }

    /**
     * Gestor aprova um pedido, sendo efetuadas as devidas alterações.
     * Qualquer evento que cause conflito com o novo, é cancelado e os interessados notificados.
     * @param nr_pedido
     * @return
     * @throws IdNotFoundException
     */
    public Pedido aceitaPedido(long nr_pedido) throws IdNotFoundException {
        //Identificamos se o pedido é alocação ou alteração
        Optional<Alocacao> a = alr.findById(nr_pedido);

        Optional<Alteracao> up = null;

        if (!a.isPresent()) up = upr.findById(nr_pedido);
        else return aceitaPedido(a.get());

        if (up == null)
            throw new IdNotFoundException("Pedido with id=" + nr_pedido + " not found.");

        return aceitaPedido(up.get());
    }

    /**
     * Gestor aprova uma alocação de um espaço(Novo Evento).
     * @param p
     * @return
     */
    private Pedido aceitaPedido(Alocacao p){

        LocalDateTime inicio = p.getDateTimeInicial();
        LocalDateTime fim = p.getDateTimeFinal();
        Evento e = new Evento(p.getNome(), p.getDescricao(), inicio, fim, p.getPeriodicidade(), p.getLimite());
        setEventRef(e, p);

        //Eliminar conflitos
        elimConflitos(e, inicio, fim);

        //Pedido aceite
        p = (Alocacao)gestor.aceitaPedido(p);

        //Notificar Responsável
        notificarResponsavel(p, "O evento " + e.getNome() + " foi criado com sucesso.");

        pr.save(p);

        return p;
    }

    /**
     * Gestor aprova a alteração de informação de um evento já existente.
     * @param p
     * @return
     */
    private Pedido aceitaPedido(Alteracao p){
        Evento e = p.getEvento();
        UtilsGHE.updateEvento(e, p);
        setEventRef(e, p);

        //Eliminar conflitos
        elimConflitos(e, e.getDateTimeInicial(), e.getDateTimeFinal());

        //Pedido aceite
        p = (Alteracao)gestor.aceitaPedido(p);

        //Notificar Responsável
        notificarResponsavel(p, "O evento " + e.getNome() + " foi atualizado com sucesso.");

        //Notificar Seguidores
        Notificacao n = new Notificacao("O evento " + e.getNome() + " teve as suas informações atualizadas.");
        e.getSeguidores().forEach(s -> s.addNotificacao(n));

        pr.save(p);

        return p;
    }


    /**
     * Gestor rejeita o pedido e notifica o responsável que o efetuou
     * @param nr_pedido
     * @param justificacao
     * @return
     * @throws IdNotFoundException
     */
    public Pedido rejeitaPedido(long nr_pedido, String justificacao) throws IdNotFoundException{
        Pedido p = pr.getOne(nr_pedido);

        if(p == null)
            throw new IdNotFoundException("Pedido with id="+nr_pedido+" not found.");

        gestor.rejeitaPedido(p);

        UtilizadorCPDR u = ucpdr.findByPedido(nr_pedido).get();
        Notificacao n = new Notificacao(justificacao);
        u.addNotificacao(n);

        pr.save(p);
        gr.save(gestor);
        ucpdr.save(u);

        return p;
    }

    /**
     * Gestor rejeita o pedido e notifica o responsável, sem justificar
     * @param nr_pedido
     * @return
     * @throws IdNotFoundException
     */
    public Pedido rejeitaPedido(long nr_pedido) throws IdNotFoundException{
        return rejeitaPedido(nr_pedido, "Proposta rejeitada.");
    }

    /**
     * Gestor cria um novo evento.
     * Qualquer evento que entre em conflito com o novo, é cancelado.
     * @param e
     * @throws EspacoDoesNotExistException
     */
    public void novoEvento(Evento e) throws EspacoDoesNotExistException{
        Espaco esp = e.getEspaco();
        if(esp == null)
            throw new EspacoDoesNotExistException("Evento with name=" + e.getNome() + " does not have Espaco associated.");

        //O gestor é o responsavel
        e.setUtilizadorResponsavel(gestor);

        //Eliminar conflitos
        elimConflitos(e, e.getDateTimeInicial(), e.getDateTimeFinal());

        er.save(e);
    }

    /**
     * Gestor atualiza um evento já existente.
     * Qualquer evento que entre em conflito com o eventual novo agendamento, é cancelado.
     * Notifica os interessados das alterações.
     * @param id_evt
     * @param novoEvento
     * @return
     * @throws IdNotFoundException
     */
    public Evento updateEvento(long id_evt, Evento novoEvento) throws IdNotFoundException{
        Evento e = er.getOne(id_evt);

        if(e == null)
            throw new IdNotFoundException("Evento with id="+id_evt+" not found.");

        String oldName = e.getNome();

        //Atualizar o evento
        gestor.updateEvento(e, novoEvento);

        //Eliminar conflitos
        elimConflitos(e, e.getDateTimeInicial(), e.getDateTimeFinal());

        //Notificar afetados
        Notificacao n = new Notificacao("O evento "+ oldName + "teve os seus dados modificados. Por favor consulte as mudanças.");
        if(e.getUtilizadorResponsavel().getId() != gestor.getId()){
            ((UtilizadorCPDR)e.getUtilizadorResponsavel()).addNotificacao(n);
        }
        e.getSeguidores().forEach(s -> s.addNotificacao(n));

        nr.save(n);

        return e;
    }

    /**
     * Define um novo espaço comum
     * @param designacao
     * @param ec
     * @return
     */
    public EspacoComum novoEC(String designacao, List<Espaco> ec){
        EspacoComum e = gestor.novoEC(designacao, ec);

        ecr.save(e);
        gr.save(gestor);

        return e;
    }

    /**
     * Atualiza a composição de um espaço comum.
     * @param id_ec
     * @param designacao
     * @param ec
     * @return
     * @throws IdNotFoundException
     */
    public EspacoComum updateEC(long id_ec, String designacao, List<Espaco> ec) throws IdNotFoundException{
        EspacoComum e = ecr.getOne(id_ec);

        if(e == null)
            throw new IdNotFoundException("EspacoComum with id="+id_ec+" not found.");

        EspacoComum newEC = gestor.updateEC(e, designacao, ec);

        ecr.save(newEC);
        gr.save(gestor);

        return newEC;
    }

    /**
     * Descarta o agrupamento de espacos especificado.
     * @param id_ec
     * @return
     * @throws IdNotFoundException
     */
    public EspacoComum removeEC(long id_ec) throws IdNotFoundException{
        EspacoComum e = ecr.getOne(id_ec);

        if(e == null)
            throw new IdNotFoundException("EspacoComum with id="+id_ec+" not found.");

        gestor.removeEC(e);
        ecr.delete(e);

        return e;
    }

    /**
     * Retorna o identificador do gestor.
     * @return
     */
    public long getGestorId(){
        return gestor.getId();
    }

    /**
     * Devolve a instância do Administrador.
     * @return
     */
    public Administrador getAdmin(){
        List<Administrador> ads = ar.findAll();
        return ads.size() > 0 ? ads.get(0) : null;
    }

    /**----------------------------------------------------------------------------------------------------------------------------------------------
     * Métodos auxiliares
     * ----------------------------------------------------------------------------------------------------------------------------------------------
     */

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
     * Cancela os eventos que entrarem em conflito com o novo.
     * Notifica os afetados.
     * @param e
     * @param inicio
     * @param fim
     */
    private void elimConflitos(Evento e, LocalDateTime inicio, LocalDateTime fim){
        getConflitos(e, e.getEspaco(), inicio, fim).forEach(ev -> {
            try{
                this.cancelaEvento(ev.getId(), "O evento "+ ev.getNome() + " foi cancelado.");
            }catch(Exception k){}
        });
    }

    /**
     * Notifica o responsável pelo pedido.
     * @param p
     * @param msg
     */
    private void notificarResponsavel(Pedido p, String msg){
        UtilizadorCPDR r = ucpdr.findByPedido(p.getId()).get();
        Notificacao n = new Notificacao(msg);
        r.addNotificacao(n);
        nr.save(n);
    }

    /**
     * Atende e aceita o pedido
     * @param p
     */
    /*private void aceita(Pedido p){
        p.setAceite(true);
        p.setAtendido(true);
    }*/

    /**
     * Especifica o evento, local, responsável, horario, e persiste a informação.
     * @param e
     * @param p
     */
    private void setEventRef(Evento e, Pedido p){
        //Especificar responsável e Local
        e.setUtilizadorResponsavel(ucpdr.findByPedido(p.getId()).get());

        Espaco novoEsp = p.getEspaco();

        //Possível espaço antigo
        Espaco esp = e.getEspaco();

        //Especificamos o novo loca
        e.setEspaco(novoEsp);
        er.save(e);

        //Se o evento estava associado a um espaco diferente do novo
        if(esp != null && esp.getId() != novoEsp.getId()){
            Horario h = esp.getHorario();
            //Removemos o evento deste horário
            h.removeEvento(e);
            hr.save(h);
        }


        //Adicionar ao hoŕário do local
        Horario h = novoEsp.getHorario();
        h.addEvento(e);
        hr.save(h);
    }

}
