package com.gestaoespacos.app.model;

import com.gestaoespacos.app.beans.*;
import com.gestaoespacos.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;

public class GHE {

    private static List<Utilizador> utilizadores;
    private static Administrador admin; //= (Administrador)ar.getOne(id do admin);?
    private static GestorEspacos gestor; //= (GestorEspacos)ar.getOne(id do gestor);?
    private static List<Espaco> espacos;
    private static List<Horario> horarios;
    private static List<Evento> eventos;

    private static AtorRepository ar;
    private static EventoRepository er;
    private static PedidoRepository pr;
    private static EspacoComumRepository ecr;

    private static ApplicationContext ctx = new AnnotationConfigApplicationContext(BeansConfig.class);
    private static VisitanteBean vb = ctx.getBean(VisitanteBean.class);
    //private static UtilizadorBean ub = ctx.getBean(UtilizadorBean.class);
    private static ResponsavelBean rb = ctx.getBean(ResponsavelBean.class);

    @Autowired
    private static UtilizadorBean ub;

    public static Evento consultarEvento(String nome) throws EventoDoesNotExistException{
        return vb.consultarEvento(nome);
    }

    public static Horario consultarHorario(String designacaoEspaco) throws EspacoDoesNotExistException{
        return vb.consultarHorario(designacaoEspaco);
    }

    public static Evento follow(long id_user, long id_evt) throws IdNotFoundException{
        return ub.follow(id_user, id_evt);
    }

    public static Evento unfollow(long id_user, long id_evt) throws IdNotFoundException{
        return ub.unfollow(id_user, id_evt);
    }

    public static List<Notificacao> getNotificacoes(long id_user) throws IdNotFoundException{
        return ub.getNotificacoes(id_user);
    }

    public static Set<Evento> getFollowing(long id_user) throws IdNotFoundException{
        return ub.getFollowing(id_user);
    }

    //TODO: Falta lidar com a condição de <1 hora
    public static Pedido alocarEspaco(long id_usercpdr, Alocacao a) throws IdNotFoundException{
        return rb.alocarEspaco(id_usercpdr, a);
    }

    public static Pedido cancelarPedido(long id_usercpdr, long nr_pedido) throws IdNotFoundException{
        return rb.cancelarPedido(id_usercpdr, nr_pedido);
    }

    //TODO: Falta lidar com a condição de <1 hora
    public static Pedido alterarEvento(long id_usercpdr, Alteracao a) throws IdNotFoundException{
        return rb.alterarEvento(id_usercpdr, a);
    }

    //TODO: Intervalos? Justificação?
    public static Evento cancelaEvento(long id_responsavel, long id_evt) throws IdNotFoundException{

        if(gestor.getId() == id_responsavel){
            Evento e = er.getOne(id_evt);

            if(e == null)
                throw new IdNotFoundException("Evento with id="+id_evt+" not found.");

            gestor.cancelaEvento(e);

            ar.save(gestor);

            return e;
        }
        else return rb.cancelaEvento(id_responsavel, id_evt);
    }

    public static List<Evento> eventosResp(long id_resp) throws IdNotFoundException{
        return rb.eventosResp(id_resp);
    }

    public static void registarUtilizador(Utilizador u) {
        ub.registarUtilizador(u);
    }

    //TODO: Lidar com conflitos
    public static Pedido aceitaPedido(long nr_pedido) throws IdNotFoundException{
        Pedido p = pr.getOne(nr_pedido);

        if(p == null)
            throw new IdNotFoundException("Pedido with id="+nr_pedido+" not found.");

        gestor.aceitaPedido(p);

        //Notificacao?

        ar.save(gestor);

        return p;
    }

    //TODO: Justificacao?
    public static Pedido rejeitaPedido(long nr_pedido) throws IdNotFoundException{
        Pedido p = pr.getOne(nr_pedido);

        if(p == null)
            throw new IdNotFoundException("Pedido with id="+nr_pedido+" not found.");

        gestor.rejeitaPedido(p);

        //Notificacao?


        ar.save(gestor);

        return p;
    }

    //TODO: Lidar com conflitos
    public static void novoEvento(Evento e){
        gestor.novoEvento(e);
        ar.save(gestor);
    }

    public static Evento updateEvento(long id_evt, Evento novoEvento) throws IdNotFoundException{
        Evento e = er.getOne(id_evt);

        if(e == null)
            throw new IdNotFoundException("Evento with id="+id_evt+" not found.");

        gestor.updateEvento(e, novoEvento);
        ar.save(gestor);

        //Notificacao?

        return novoEvento;
    }

    public static EspacoComum novoEC(String designacao, List<Espaco> ec){
        EspacoComum e = gestor.novoEC(designacao, ec);

        ecr.save(e);
        ar.save(gestor);

        return e;
    }

    public static EspacoComum updateEC(long id_ec, String designacao, List<Espaco> ec) throws IdNotFoundException{
        EspacoComum e = ecr.getOne(id_ec);

        if(e == null)
            throw new IdNotFoundException("EspacoComum with id="+id_ec+" not found.");

        EspacoComum newEC = gestor.updateEC(e, designacao, ec);

        ecr.save(newEC);
        ar.save(gestor); //?

        return newEC;
    }

    public static EspacoComum removeEC(long id_ec)throws IdNotFoundException{
        EspacoComum e = ecr.getOne(id_ec);

        if(e == null)
            throw new IdNotFoundException("EspacoComum with id="+id_ec+" not found.");

        gestor.removeEC(e); //?
        ecr.delete(e); //? São necessários ambos?
        ar.save(gestor); //? Se fizermos 'save' do gestor, apos removeEC, significa q n precisamos de explicitamente fazer ecr.delete ??????

        return e;
    }

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
