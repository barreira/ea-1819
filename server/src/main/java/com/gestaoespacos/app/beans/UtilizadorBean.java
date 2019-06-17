package com.gestaoespacos.app.beans;

import com.gestaoespacos.app.model.Evento;
import com.gestaoespacos.app.model.IdNotFoundException;
import com.gestaoespacos.app.model.Notificacao;
import com.gestaoespacos.app.model.Utilizador;
import com.gestaoespacos.app.repositories.AtorRepository;
import com.gestaoespacos.app.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class UtilizadorBean {

    @Autowired
    private AtorRepository ar;
    @Autowired
    private EventoRepository er;

    public Evento follow(long id_user, long id_evt) throws IdNotFoundException {
        Utilizador u = (Utilizador)ar.getOne(id_user);

        if(u == null)
            throw new IdNotFoundException("Utilizador with id="+id_user+" not found.");

        Evento e = er.getOne(id_evt);

        if(e == null)
            throw new IdNotFoundException("Evento with id="+id_evt+" not found.");

        u.follow(e);

        er.save(e); //?
        ar.save(u);

        return e;
    }

    public Evento unfollow(long id_user, long id_evt) throws IdNotFoundException{
        Utilizador u = (Utilizador)ar.getOne(id_user);

        if(u == null)
            throw new IdNotFoundException("Utilizador with id="+id_user+" not found.");

        Evento e = er.getOne(id_evt);

        if(e == null)
            throw new IdNotFoundException("Evento with id="+id_evt+" not found.");

        u.unfollow(e);

        er.save(e); //?
        ar.save(u);

        return e;
    }

    public List<Notificacao> getNotificacoes(long id_user) throws IdNotFoundException{
        Utilizador u = (Utilizador)ar.getOne(id_user);//ur.getOne(id_user);

        if(u == null)
            throw new IdNotFoundException("Utilizador with id="+id_user+" not found.");

        return u.getNotificacoes();
    }

    public Set<Evento> getFollowing(long id_user) throws IdNotFoundException{
        Utilizador u = (Utilizador)ar.getOne(id_user);//ur.getOne(id_user);

        if(u == null)
            throw new IdNotFoundException("Utilizador with id="+id_user+" not found.");

        return u.getEventosASeguir();
    }

    public void registarUtilizador(Utilizador u) {
        ar.save(u);
    }
}
