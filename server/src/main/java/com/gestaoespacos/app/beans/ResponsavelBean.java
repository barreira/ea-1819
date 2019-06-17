package com.gestaoespacos.app.beans;

import com.gestaoespacos.app.model.*;
import com.gestaoespacos.app.repositories.AtorRepository;
import com.gestaoespacos.app.repositories.EventoRepository;
import com.gestaoespacos.app.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ResponsavelBean {

    @Autowired
    private AtorRepository ar;
    @Autowired
    private PedidoRepository pr;
    @Autowired
    private EventoRepository er;

    public Pedido alocarEspaco(long id_usercpdr, Alocacao a) throws IdNotFoundException {
        UtilizadorCPDR u = (UtilizadorCPDR)ar.getOne(id_usercpdr);

        if(u == null)
            throw new IdNotFoundException("UtilizadorCPDR with id="+id_usercpdr+" not found.");

        u.alocarEspaco(a);

        pr.save(a); //?
        ar.save(u);

        return a;
    }

    public Pedido cancelarPedido(long id_usercpdr, long nr_pedido) throws IdNotFoundException{
        UtilizadorCPDR u = (UtilizadorCPDR)ar.getOne(id_usercpdr);

        if(u == null)
            throw new IdNotFoundException("UtilizadorCPDR with id="+id_usercpdr+" not found.");

        Pedido p = pr.getOne(nr_pedido);

        if(p == null)
            throw new IdNotFoundException("Pedido with id="+nr_pedido+" not found.");

        u.cancelaPedido(p);

        pr.delete(p); //?
        ar.save(u);

        return p;
    }

    public Pedido alterarEvento(long id_usercpdr, Alteracao a) throws IdNotFoundException{
        UtilizadorCPDR u = (UtilizadorCPDR)ar.getOne(id_usercpdr);

        if(u == null)
            throw new IdNotFoundException("UtilizadorCPDR with id="+id_usercpdr+" not found.");

        u.alterarEvento(a);

        pr.save(a); //?
        ar.save(u);

        return a;
    }

    //Garantir que o responsável não é o Gestor
    public Evento cancelaEvento(long id_responsavel, long id_evt) throws IdNotFoundException{
        Responsavel r = (Responsavel)ar.getOne(id_responsavel);

        if(r == null)
            throw new IdNotFoundException("Responsavel with id="+id_responsavel+" not found.");

        Evento e = er.getOne(id_evt);

        if(e == null)
            throw new IdNotFoundException("Evento with id="+id_evt+" not found.");

        r.cancelaEvento(e);

        ar.save((UtilizadorCPDR)r);

        return e;
    }

    public List<Evento> eventosResp(long id_resp) throws IdNotFoundException{
        Responsavel r = (Responsavel)ar.getOne(id_resp);

        if(r == null)
            throw new IdNotFoundException("Responsavel with id="+id_resp+" not found.");

        return r.meusEventos();
    }
}
