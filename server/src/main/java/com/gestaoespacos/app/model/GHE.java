package com.gestaoespacos.app.model;

import com.gestaoespacos.app.beans.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GHE {

    /**
     * Classes de Servico
     */
    private static UtilizadorBean ub;
    private static VisitanteBean vb;
    private static ResponsavelBean rb;
    private static GestorBean gb;

    private static Administrador admin;

    @Autowired
    public GHE(UtilizadorBean userBean, VisitanteBean visitanteBean, ResponsavelBean respBean, GestorBean gesBean){
        this.ub = userBean;
        this.vb = visitanteBean;
        this.rb = respBean;
        this.gb = gesBean;
        this.admin = gb.getAdmin();
    }

    /**
     * Obter um evento com o nome fornecido, a existir.
     * @param nome do evento pretendido
     * @return
     * @throws EventoDoesNotExistException
     */
    public static Evento consultarEvento(String nome) throws EventoDoesNotExistException{
        return vb.consultarEvento(nome);
    }

    /**
     * Obter o horário associado ao espaco com determinada designação, a existir.
     * @param designacaoEspaco
     * @return
     * @throws EspacoDoesNotExistException
     */
    public static Horario consultarHorario(String designacaoEspaco) throws EspacoDoesNotExistException{
        return vb.consultarHorario(designacaoEspaco);
    }

    /**
     * Utilizador passa a seguir o evento
     * Caso já estivesse a seguir o evento, não causa alterações
     * @param id_user
     * @param id_evt
     * @return
     * @throws IdNotFoundException
     */
    public static Evento follow(long id_user, long id_evt) throws IdNotFoundException{
        return ub.follow(id_user, id_evt);
    }

    /**
     * Utilizador deixa de seguir o evento, se o estava a seguir previamente
     * @param id_user
     * @param id_evt
     * @return
     * @throws IdNotFoundException
     */
    public static Evento unfollow(long id_user, long id_evt) throws IdNotFoundException{
        return ub.unfollow(id_user, id_evt);
    }

    /**
     * Obter as notificações recebidas pelo utilizador
     * @param id_user
     * @return
     * @throws IdNotFoundException
     */
    public static List<Notificacao> getNotificacoes(long id_user) throws IdNotFoundException{
        return ub.getNotificacoes(id_user);
    }

    /**
     * Determina o conjunto de eventos que o utilizador especificado segue
     * @param id_user
     * @return
     * @throws IdNotFoundException
     */
    public static Set<Evento> getFollowing(long id_user) throws IdNotFoundException{
        return ub.getFollowing(id_user);
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
    public static Pedido alocarEspaco(long id_usercpdr, Alocacao a) throws IdNotFoundException{
        return rb.alocarEspaco(id_usercpdr, a);
    }

    /**
     * Responsável decidiu cancelar um pedido pedente efetuado por ele
     * @param id_usercpdr
     * @param nr_pedido
     * @return
     * @throws IdNotFoundException
     */
    public static Pedido cancelarPedido(long id_usercpdr, long nr_pedido) throws IdNotFoundException{
        return rb.cancelarPedido(id_usercpdr, nr_pedido);
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
    public static Pedido alterarEvento(long id_usercpdr, Alteracao a) throws IdNotFoundException{
        return rb.alterarEvento(id_usercpdr, a);
    }

    /**
     * O responsável pelo evento cancela o mesmo, notificando os seguidores.
     * @param id_responsavel
     * @param id_evt
     * @param justificacao
     * @return
     * @throws IdNotFoundException
     */
    public static Evento cancelaEvento(long id_responsavel, long id_evt, String justificacao) throws IdNotFoundException{

        if(gb.getGestorId() == id_responsavel){
            return gb.cancelaEvento(id_evt, justificacao);
        }
        else return rb.cancelaEvento(id_responsavel, id_evt, justificacao);
    }

    /**
     * O responsável pelo evento cancela o mesmo, notificando os seguidores.
     * Não fornece uma justificação personalizada.
     * @param id_responsavel
     * @param id_evt
     * @return
     * @throws IdNotFoundException
     */
    public static Evento cancelaEvento(long id_responsavel, long id_evt) throws IdNotFoundException{

        if(gb.getGestorId() == id_responsavel){
            return gb.cancelaEvento(id_evt);
        }
        else return rb.cancelaEvento(id_responsavel, id_evt);
    }

    /**
     * O gestor cancela o evento, notifica o responsável e seguidores desse evento,
     * não fornecendo uma justificação personalizada.
     * @param id_evt
     * @return
     * @throws IdNotFoundException
     */
    public static Evento cancelaEvento(long id_evt) throws IdNotFoundException{
        return gb.cancelaEvento(id_evt);
    }

    /**
     * O gestor cancela o evento, notifica o responsável e seguidores desse evento.
     * @param id_evt
     * @param justificacao
     * @return
     * @throws IdNotFoundException
     */
    public static Evento cancelaEvento(long id_evt, String justificacao) throws IdNotFoundException{
        return gb.cancelaEvento(id_evt, justificacao);
    }

    /**
     * Determina os eventos que são geridos pelo responsável especificado
     * @param id_resp
     * @return
     * @throws IdNotFoundException
     */
    public static List<Evento> eventosResp(long id_resp) throws IdNotFoundException{
        if(gb.getGestorId() == id_resp){
            return gb.eventosResp();
        }
        else return rb.eventosResp(id_resp);
    }


    /**
     * Regista utilizador no sistema
     * @param u
     */
    public static void registarUtilizador(Utilizador u) {
        ub.registarUtilizador(u);
    }

    /**
     * Gestor aprova um pedido, sendo efetuadas as devidas alterações.
     * Qualquer evento que cause conflito com o novo, é cancelado e os interessados notificados.
     * @param nr_pedido
     * @return
     * @throws IdNotFoundException
     */
    public static Pedido aceitaPedido(long nr_pedido) throws IdNotFoundException{
        return gb.aceitaPedido(nr_pedido);
    }

    /**
     *
     * @param nr_pedido
     * @return Gestor rejeita o pedido e notifica o responsável, sem justificar
     * @throws IdNotFoundException
     */
    public static Pedido rejeitaPedido(long nr_pedido) throws IdNotFoundException{
        return gb.rejeitaPedido(nr_pedido);
    }

    /**
     * Gestor rejeita o pedido e notifica o responsável que o efetuou
     * @param nr_pedido
     * @param justificacao
     * @return
     * @throws IdNotFoundException
     */
    public static Pedido rejeitaPedido(long nr_pedido, String justificacao) throws IdNotFoundException{
        return gb.rejeitaPedido(nr_pedido, justificacao);
    }

    /**
     * Gestor cria um novo evento.
     * Qualquer evento que entre em conflito com o novo, é cancelado.
     * @param e
     * @throws EspacoDoesNotExistException
     */
    public static void novoEvento(Evento e) throws  EspacoDoesNotExistException{
        gb.novoEvento(e);
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
    public static Evento updateEvento(long id_evt, Evento novoEvento) throws IdNotFoundException{
        return gb.updateEvento(id_evt, novoEvento);
    }

    /**
     * Define um novo espaço comum.
     * @param designacao
     * @param ec
     * @return
     */
    public static EspacoComum novoEC(String designacao, List<Espaco> ec){
        return gb.novoEC(designacao, ec);
    }

    /**
     * Atualiza a composição de um espaço comum.
     * @param id_ec
     * @param designacao
     * @param ec
     * @return
     * @throws IdNotFoundException
     */
    public static EspacoComum updateEC(long id_ec, String designacao, List<Espaco> ec) throws IdNotFoundException{
        return gb.updateEC(id_ec, designacao, ec);
    }

    /**
     * Descarta o agrupamento de espacos especificado.
     * @param id_ec
     * @return
     * @throws IdNotFoundException
     */
    public static EspacoComum removeEC(long id_ec) throws IdNotFoundException{
        return gb.removeEC(id_ec);
    }

    //TODO: implementar métodos de carregamento de dados
    public static void carregarAtores(String filename){
        admin.carregarAtores(filename);
    }

    public static void carregarEspacos(String filename){
        admin.carregarEspacos(filename);
    }

    public static void carregarHorarios(String filename){
        admin.carregarHorarios(filename);
    }
}
