package com.gestaoespacos.app.model;

import com.gestaoespacos.app.beans.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private static CalendarBean cb;

    private static Administrador admin;

    @Autowired
    public GHE(UtilizadorBean userBean, VisitanteBean visitanteBean, ResponsavelBean respBean, GestorBean gesBean, CalendarBean cb){
        this.ub = userBean;
        this.vb = visitanteBean;
        this.rb = respBean;
        this.gb = gesBean;
        this.cb = cb;
        this.admin = gb.getAdmin();
    }

    /**
     * Obter um evento com o nome fornecido, a existir.
     * @param nome do evento pretendido
     * @return evento
     * @throws EventoDoesNotExistException não existe evento com esse nome
     */
    public static Evento consultarEvento(String nome) throws EventoDoesNotExistException{
        return vb.consultarEvento(nome);
    }

    /**
     * Obter o horário associado ao espaco com determinada designação, a existir.
     * @param designacaoEspaco do espaço
     * @return espaço
     * @throws EspacoDoesNotExistException não existe espaço com essa designação
     */
    public static Horario consultarHorario(String designacaoEspaco) throws EspacoDoesNotExistException{
        return vb.consultarHorario(designacaoEspaco);
    }

    /**
     * Obter todos os eventos entre datas, agrupados por data.
     * @param inicio data de inicio
     * @param fim data de fim
     * @return Conjunto de eventos agrupados por dia
     */
    public static Map<LocalDate, Set<Evento>> eventosEntreDatas(LocalDate inicio, LocalDate fim){
        return vb.eventosEntreDatas(inicio, fim);
    }

    /**
     * Obter todos os eventos de um espaço entre duas datas, agrupados por data.
     * @param inicio data de inicio
     * @param fim data de fim
     * @param espaco espaço em questão
     * @return Conjunto de eventos desse espaço agrupados por dia
     * @throws IdNotFoundException não existe espaço com esse identificador
     */
    public static Map<LocalDate, Set<Evento>> eventosEntreDatas(LocalDate inicio, LocalDate fim, long espaco) throws  IdNotFoundException{
        return vb.eventosEntreDatas(inicio, fim, espaco);
    }

    /**
     * Utilizador passa a seguir o evento
     * Caso já estivesse a seguir o evento, não causa alterações
     * @param id_user Utilizador
     * @param id_evt Evento
     * @return Evento
     * @throws IdNotFoundException Utilizador/Evento com esse identificador não encontrados
     */
    public static Evento follow(long id_user, long id_evt) throws IdNotFoundException{
        return ub.follow(id_user, id_evt);
    }

    /**
     * Utilizador deixa de seguir o evento, se o estava a seguir previamente
     * @param id_user Utilizador
     * @param id_evt Evento
     * @return Evento
     * @throws IdNotFoundException Utilizador/Evento com esse identificador não encontrados
     */
    public static Evento unfollow(long id_user, long id_evt) throws IdNotFoundException{
        return ub.unfollow(id_user, id_evt);
    }

    /**
     * Obter as notificações recebidas pelo utilizador
     * @param id_user Utilizador
     * @return Notificações
     * @throws IdNotFoundException Utilizador com esse identificador não encontrado
     */
    public static Set<Notificacao> getNotificacoes(long id_user) throws IdNotFoundException{
        return ub.getNotificacoes(id_user);
    }

    /**
     * Determina o conjunto de eventos que o utilizador especificado segue
     * @param id_user Utilizador
     * @return Eventos
     * @throws IdNotFoundException Utilizador com esse identificador não encontrado
     */
    public static Set<Evento> getFollowing(long id_user) throws IdNotFoundException{
        return ub.getFollowing(id_user);
    }

    /**
     * O utilizador com privilegios de requisição efetua o pedido de alocação de espaço, a ser aprovado pelo gestor
     * Caso falte menos de 1 hora para o inicio do evento e este último não causar conflito com outros, será
     * automaticamente efetivado.
     * @param id_usercpdr Utilizador c/ Privilegios
     * @param a Alocação
     * @return Pedido
     * @throws IdNotFoundException  Utilizador com esse identificador não encontrado
     */
    public static Pedido alocarEspaco(long id_usercpdr, Alocacao a) throws IdNotFoundException{
        return rb.alocarEspaco(id_usercpdr, a);
    }

    /**
     * Responsável decidiu cancelar um pedido pedente efetuado por ele
     * @param id_usercpdr Utilizador c/ Privilegios
     * @param nr_pedido Pedido
     * @return Pedido
     * @throws IdNotFoundException Utilizador/Pedido com esse identificador não encontrado
     */
    public static Pedido cancelarPedido(long id_usercpdr, long nr_pedido) throws IdNotFoundException{
        return rb.cancelarPedido(id_usercpdr, nr_pedido);
    }

    /**
     * O utilizador com privilegios de requisição efetua um pedido de alteração de um evento pelo qual é responsável.
     * Se faltar menos de 1 hora para o inicio proposto deste evento, e a sua nova especificação não causar conflito
     * com outros, será automaticamente aceite. Senão, ficará à espera de atendimento por parte do gestor.
     * @param id_usercpdr  Utilizador c/ Privilegios
     * @param a Alteracao
     * @return Pedido
     * @throws IdNotFoundException Utilizador com esse identificador não encontrado
     */
    public static Pedido alterarEvento(long id_usercpdr, Alteracao a) throws IdNotFoundException{
        return rb.alterarEvento(id_usercpdr, a);
    }

    /**
     * Determina os pedidos atendidos efetuados por este utilizador c/ privilegios.
     * Se não existir nenhum utilizador com esse identificador associado, é retornada a lista vazia.
     * @param id_usercpdr Utilizador
     * @return Pedidos atendidos
     */
    public static List<Pedido> getAtendidos(long id_usercpdr){
        return rb.getPedidosByAtendimento(id_usercpdr, true);
    }

    /**
     * Determina os pedidos pendentes efetuados por este utilizador c/ privilegios.
     * Se não existir nenhum utilizador com esse identificador associado, é retornada a lista vazia.
     * @param id_usercpdr Utilizador
     * @return Pedidos pendentes
     */
    public static List<Pedido> getPendentes(long id_usercpdr){
        return rb.getPedidosByAtendimento(id_usercpdr, false);
    }

    /**
     * O responsável pelo evento cancela o mesmo, notificando os seguidores.
     * @param id_responsavel Responsável
     * @param id_evt Evento
     * @param justificacao Justificação para o cancelamento
     * @return Evento
     * @throws IdNotFoundException Responsavel/Evento com esse identificador não encontrado
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
     * @param id_responsavel Responsável
     * @param id_evt Evento
     * @return Evento
     * @throws IdNotFoundException Responsavel/Evento com esse identificador não encontrado
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
     * @param id_evt Evento
     * @return Evento
     * @throws IdNotFoundException Evento com esse identificador não encontrado
     */
    public static Evento cancelaEvento(long id_evt) throws IdNotFoundException{
        return gb.cancelaEvento(id_evt);
    }

    /**
     * O gestor cancela o evento, notifica o responsável e seguidores desse evento.
     * @param id_evt Evento
     * @param justificacao Justificação para o cancelamento
     * @return Evento
     * @throws IdNotFoundException Evento com esse identificador não encontrado
     */
    public static Evento cancelaEvento(long id_evt, String justificacao) throws IdNotFoundException{
        return gb.cancelaEvento(id_evt, justificacao);
    }

    /**
     * Determina os eventos que são geridos pelo responsável especificado
     * @param id_resp Responsável
     * @return Eventos
     * @throws IdNotFoundException Responsavel com esse identificador não encontrado
     */
    public static List<Evento> eventosResp(long id_resp) throws IdNotFoundException{
        Long gestorId = gb.getGestorId();
        if(gestorId != null && gestorId == id_resp){
            return gb.eventosResp();
        }
        else return rb.eventosResp(id_resp);
    }


    /**
     * Regista utilizador no sistema
     * @param u Utilizador
     */
    public static void registarUtilizador(Utilizador u) {
        ub.registarUtilizador(u);
    }

    /**
     * Gestor aprova um pedido, sendo efetuadas as devidas alterações.
     * Qualquer evento que cause conflito com o novo, é cancelado e os interessados notificados.
     * @param nr_pedido Pedido
     * @return Pedido aceite
     * @throws IdNotFoundException Pedido com esse identificador não encontrado
     */
    public static Pedido aceitaPedido(long nr_pedido) throws IdNotFoundException{
        return gb.aceitaPedido(nr_pedido);
    }

    /**
     * Gestor rejeita o pedido e notifica o responsável, sem justificar
     * @param nr_pedido Pedido
     * @return  Pedido atendido
     * @throws IdNotFoundException Pedido com esse identificador não encontrado
     */
    public static Pedido rejeitaPedido(long nr_pedido) throws IdNotFoundException{
        return gb.rejeitaPedido(nr_pedido);
    }

    /**
     * Gestor rejeita o pedido e notifica o responsável que o efetuou
     * @param nr_pedido Pedido
     * @param justificacao justificação
     * @return Pedido atendido
     * @throws IdNotFoundException Pedido com esse identificador não encontrado
     */
    public static Pedido rejeitaPedido(long nr_pedido, String justificacao) throws IdNotFoundException{
        return gb.rejeitaPedido(nr_pedido, justificacao);
    }

    /**
     * Gestor cria um novo evento.
     * Qualquer evento que entre em conflito com o novo, é cancelado.
     * @param e Evento
     * @throws EspacoDoesNotExistException Evento não tem um espaço válido associado
     */
    public static void novoEvento(Evento e) throws  EspacoDoesNotExistException{
        gb.novoEvento(e);
    }

    /**
     * Gestor atualiza um evento já existente.
     * Qualquer evento que entre em conflito com o eventual novo agendamento, é cancelado.
     * Notifica os interessados das alterações.
     * @param id_evt Evento existente
     * @param novoEvento Novas informações do evento
     * @return Evento atualizado
     * @throws IdNotFoundException Evento com esse identificador não encontrado
     */
    public static Evento updateEvento(long id_evt, Evento novoEvento) throws IdNotFoundException, EspacoDoesNotExistException{
        return gb.updateEvento(id_evt, novoEvento);
    }

    /**
     * Define um novo espaço comum.
     * @param designacao do agrumento de espaços
     * @param ec Espaços que constituem o agrupamento
     * @return EspacoComum
     */
    public static EspacoComum novoEC(String designacao, List<Espaco> ec){
        return gb.novoEC(designacao, ec);
    }

    /**
     * Atualiza a composição de um espaço comum.
     * @param id_ec EspacoComum
     * @param designacao Nova desig do espaço comum
     * @param ec Nova constituição do agrupamento
     * @return EspacoComum atualizado
     * @throws IdNotFoundException EspacoComum com esse identificador não encontrado
     */
    public static EspacoComum updateEC(long id_ec, String designacao, List<Espaco> ec) throws IdNotFoundException{
        return gb.updateEC(id_ec, designacao, ec);
    }

    /**
     * Descarta o agrupamento de espacos especificado.
     * @param id_ec EspacoComum
     * @return EspacoComum removido
     * @throws IdNotFoundException EspacoComum com esse identificador não encontrado
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

    /**
     * Efetua a sincronização do Primary Google Calendar do utilizador, tendo em conta os eventos que está a seguir
     * @param id do utilizador
     * @param code para obter o token com o google
     * @return sucesso na sincronização
     * @throws IdNotFoundException se não existir Utilizador(CPDR) com esse id
     */
    public static boolean syncCalendar(long id, String code) throws IdNotFoundException{
        return cb.syncCalendar(id, code);
    }
}
