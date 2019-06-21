package com.gestaoespacos.app.beans;

import com.gestaoespacos.app.model.*;
import com.gestaoespacos.app.repositories.EspacoRepository;
import com.gestaoespacos.app.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(value= ConfigurableBeanFactory.SCOPE_SINGLETON)
public class VisitanteBean {

    private EventoRepository er;
    private EspacoRepository sr;

    @Autowired
    public VisitanteBean(EspacoRepository sr, EventoRepository er){
        this.sr = sr;
        this.er = er;
    }

    /**
     * Obter um evento com o nome fornecido, a existir
     * @param nome
     * @return
     * @throws EventoDoesNotExistException
     */
    public Evento consultarEvento(String nome) throws EventoDoesNotExistException {
        List<Evento> es = er.findByNome(nome);

        if(es.size() > 0){
            return es.get(0);
        }
        else throw new EventoDoesNotExistException();
    }

    /**
     * Obter o horário associado ao espaco com determinada designação, a existir.
     * @param designacaoEspaco
     * @return
     * @throws EspacoDoesNotExistException
     */
    public Horario consultarHorario(String designacaoEspaco) throws EspacoDoesNotExistException {
        List<Espaco> es = sr.findByDesignacao(designacaoEspaco);

        if(es.size() > 0){
            return es.get(0).getHorario();
        }
        else throw new EspacoDoesNotExistException();
    }
}
