package com.gestaoespacos.app.beans;

import com.gestaoespacos.app.model.*;
import com.gestaoespacos.app.repositories.EspacoRepository;
import com.gestaoespacos.app.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * Obter todos os eventos entre datas, agrupados por data.
     * @param i
     * @param f
     * @return
     */
    public Map<LocalDate, Set<Evento>> eventosEntreDatas(LocalDate i, LocalDate f){
        return eventosEntreDatas(i, f, null);
    }

    /**
     * Obter todos os eventos de um dado espaço entre duas datas, agrupados por data.
     * @param i
     * @param f
     * @param espaco
     * @return
     * @throws IdNotFoundException
     */
    public Map<LocalDate, Set<Evento>> eventosEntreDatas(LocalDate i, LocalDate f, long espaco) throws IdNotFoundException{
        Espaco esp = sr.getOne(espaco);

        if(esp == null)
            throw new IdNotFoundException("Espaco with id="+espaco+" not found.");

        return eventosEntreDatas(i, f, esp);
    }

    /**
     * Obter os eventos entre duas datas, agrupados pelo dia.
     * Se o espaço for especificado, então filtramos por espaço, caso contŕario, consideram-se todos.
     * @param i inicio
     * @param f fim
     * @param esp possível espaço (pode ser null)
     * @return horario de eventos
     */
    private Map<LocalDate, Set<Evento>> eventosEntreDatas(LocalDate i, LocalDate f, Espaco esp){
        LocalDateTime inicio = LocalDateTime.of(i, LocalTime.MIN);
        LocalDateTime fim = LocalDateTime.of(f, LocalTime.MAX);
        Map<LocalDate, Set<Evento>> eventos = new TreeMap<>();

        //Obter eventos não periódicos que pertencem ao intervalo
        Set<Evento> oneTimeEvents = esp == null ?
                er.findAllByDateTimeInicialGreaterThanEqualAndDateTimeFinalLessThanEqualAndPeriodicidadeEquals(inicio, fim, 0) :
                er.findAllByDateTimeInicialGreaterThanEqualAndDateTimeFinalLessThanEqualAndPeriodicidadeEqualsAndEspaco(inicio, fim, 0, esp);

        oneTimeEvents.forEach(ev -> addToMap(eventos, ev, ev.getDateTimeInicial().toLocalDate()));

        //Candidatos: Eventos periódicos que terminam após a data de inicio
        Set<Evento> perEvents = esp == null ?
                er.findAllByPeriodicidadeGreaterThanAndLimiteGreaterThanEqual(0, inicio) :
                er.findAllByPeriodicidadeGreaterThanAndLimiteGreaterThanEqualAndEspaco(0, inicio, esp);

        for(Evento e : perEvents){
            LocalDateTime current = e.getDateTimeInicial();
            LocalDate limite = e.getLimite().toLocalDate();
            int periodo = e.getPeriodicidade();

            //Posicionar a data no intervalo
            while(current.isBefore((inicio)))
                current = current.plusDays(periodo);

            //Equanto ocorrer dentro do intervalo
            while((current.isEqual(inicio) || inicio.isBefore(current)) &&
                    (current.isEqual(fim) || current.isBefore(fim)) &&
                    (current.toLocalDate().isEqual(limite) || current.toLocalDate().isBefore(limite))
            ){
                //Adicionamos à estrutura
                addToMap(eventos, e, current.toLocalDate());

                //Iteramos no período
                current = current.plusDays(periodo);
            }

        }


        return eventos;
    }


    private void addToMap(Map<LocalDate, Set<Evento>> eventos, Evento e, LocalDate data){
        if(!eventos.containsKey(data)){
            Set<Evento> evsDia = new TreeSet<>(Comparator.comparing(Evento :: getDateTimeInicial)
                                                         .thenComparing(Evento :: getDateTimeFinal)
                                                         .thenComparing(Evento :: getId));

            evsDia.add(e);
            eventos.put(data, evsDia);
        }
        else eventos.get(data).add(e);

    }

}
