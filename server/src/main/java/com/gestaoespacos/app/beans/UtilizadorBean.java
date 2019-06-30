package com.gestaoespacos.app.beans;

import com.gestaoespacos.app.model.*;
import com.gestaoespacos.app.repositories.UserRepository;
import com.gestaoespacos.app.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Scope(value=ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UtilizadorBean {

    private UserRepository ur;
    private EventoRepository er;

    @Autowired
    public UtilizadorBean(UserRepository ur, EventoRepository er){
        this.ur = ur;
        this.er = er;
    }

    /**
     * Utilizador passa a seguir o evento
     * @param id_user
     * @param id_evt
     * @return
     * @throws IdNotFoundException
     */
    public Evento follow(long id_user, long id_evt) throws IdNotFoundException {
        Optional<Utilizador> u_opt = ur.findById(id_user);

        if(!u_opt.isPresent())
            throw new IdNotFoundException("Utilizador with id="+id_user+" not found.");

        Utilizador u = u_opt.get();

        Optional<Evento> e_opt = er.findById(id_evt);

        if(!e_opt.isPresent())
            throw new IdNotFoundException("Evento with id="+id_evt+" not found.");

        Evento e = e_opt.get();

        u.follow(e);

        ur.save(u);

        return e;
    }

    /**
     * Utilizador deixa de seguir o evento, se o estava a seguir previamente
     * @param id_user
     * @param id_evt
     * @return
     * @throws IdNotFoundException
     */
    public Evento unfollow(long id_user, long id_evt) throws IdNotFoundException{
        Optional<Utilizador> u_opt = ur.findById(id_user);

        if(!u_opt.isPresent())
            throw new IdNotFoundException("Utilizador with id="+id_user+" not found.");

        Utilizador u = u_opt.get();

        Evento e = er.getOne(id_evt);

        if(e == null)
            throw new IdNotFoundException("Evento with id="+id_evt+" not found.");

        u.unfollow(e);

        ur.save(u);

        return e;
    }

    /**
     * Obter as notificações recebidas pelo utilizador
     * @param id_user
     * @return
     * @throws IdNotFoundException
     */
    public List<Notificacao> getNotificacoes(long id_user) throws IdNotFoundException{
        Optional<Utilizador> u_opt = ur.findById(id_user);

        if(!u_opt.isPresent())
            throw new IdNotFoundException("Utilizador with id="+id_user+" not found.");

        Utilizador u = u_opt.get();

        return u.getNotificacoes();
    }

    /**
     * Determina o conjunto de eventos que o utilizador especificado segue
     * @param id_user
     * @return
     * @throws IdNotFoundException
     */
    public Set<Evento> getFollowing(long id_user) throws IdNotFoundException{
        Optional<Utilizador> u = ur.findById(id_user);

        if(!u.isPresent())
            throw new IdNotFoundException("Utilizador with id="+id_user+" not found.");

        return u.get().getEventosASeguir();
    }

    /**
     * Regista utilizador no sistema
     * @param u
     */
    public void registarUtilizador(Utilizador u) {
        ur.save(u);
    }

}
