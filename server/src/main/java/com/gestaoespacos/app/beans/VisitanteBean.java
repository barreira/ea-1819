package com.gestaoespacos.app.beans;

import com.gestaoespacos.app.model.*;
import com.gestaoespacos.app.repositories.EspacoRepository;
import com.gestaoespacos.app.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VisitanteBean {

    @Autowired
    private EventoRepository er;
    @Autowired
    private EspacoRepository sr;

    public Evento consultarEvento(String nome) throws EventoDoesNotExistException {
        List<Evento> es = er.findByNome(nome);

        if(es.size() > 0){
            return es.get(0);
        }
        else throw new EventoDoesNotExistException();
    }

    public Horario consultarHorario(String designacaoEspaco) throws EspacoDoesNotExistException {
        List<Espaco> es = sr.findByDesignacao(designacaoEspaco);

        if(es.size() > 0){
            return es.get(0).getHorario();
        }
        else throw new EspacoDoesNotExistException();
    }
}
