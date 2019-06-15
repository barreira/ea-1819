package com.gestaoespacos.app.model;

import java.util.*;

public class GHE {

    private List<Utilizador> utilizadores;
    private Administrador admin;
    private GestorEspacos gestor;
    private List<Espaco> espacos;
    private List<Horario> horarios;
    private List<Evento> eventos;

    public static Evento consultarEvento(String nome){
        return null;
    }

    public static Horario consultarHorario(String designacaoEspaco){
        return null;
    }

    public static Evento follow(long id_user, long id_evt){
        return null;
    }

    public static Evento unfollow(long id_user, long id_evt){
        return null;
    }

    public static List<Notificacao> getNotificacoes(long id_user){
        return null;
    }

    public static List<Evento> getFollowing(long id_user){
        return null;
    }

    public static Pedido alocarEspaco(long id_usercpdr, Alocacao a){
        return null;
    }

    public static Pedido cancelarPedido(long id_usercpdr, long nr_pedido){
        return null;
    }

    public static Pedido alterarEspaco(long id_usercpdr, Alteracao a){
        return null;
    }

    public static Evento cancelaEvento(long id_responsavel, long id_evt){
        return null;
    }

    public static List<Evento> eventosResp(long id_resp){
        return null;
    }

    public static void registarUtilizador(Utilizador u){

    }

    public static Pedido aceitaPedido(long nr_pedido){
        return null;
    }

    public static Pedido rejeitaPedido(long nr_pedido){
        return null;
    }

    public static void novoEvento(Evento e){

    }

    public static Evento updateEvento(long id_evt, Evento novoEvento){
        return null;
    }

    public static EspacoComum novoEC(String designacao, List<Espaco> ec){
        return null;
    }

    public static EspacoComum updateEC(long id_ec, String designacao, List<Espaco> ec){
        return null;
    }

    public static EspacoComum removeEC(long id_ec){
        return null;
    }
}
