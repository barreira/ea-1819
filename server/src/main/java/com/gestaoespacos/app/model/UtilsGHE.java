package com.gestaoespacos.app.model;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.UnsupportedTemporalTypeException;

public class UtilsGHE {
    //Verifica se o Temporal está contido num intrevalo
    public static boolean isBetween(Temporal t, Temporal tInicio, Temporal tFim){
        String tClass = t.getClass().getName();
        String tIClass = tInicio.getClass().getName();
        String tFClass = tFim.getClass().getName();

        if(tClass.equals(tIClass) && tClass.equals(tFClass)){
            boolean res = false;

            switch(tClass){
                case "java.time.LocalDate":
                    res = ((LocalDate)t).isAfter((LocalDate)tInicio) & ((LocalDate)t).isBefore((LocalDate)tFim);
                    break;
                case "java.time.LocalTime":
                    res = ((LocalTime)t).isAfter((LocalTime)tInicio) & ((LocalTime)t).isBefore((LocalTime)tFim);
                    break;
                case "java.time.LocalDateTime":
                    res = ((LocalDateTime)t).isAfter((LocalDateTime)tInicio) & ((LocalDateTime)t).isBefore((LocalDateTime)tFim);
                    break;
                case "java.time.ZonedDateTime":
                    res = ((ZonedDateTime)t).isAfter((ZonedDateTime)tInicio) & ((ZonedDateTime)t).isBefore((ZonedDateTime)tFim);
                    break;
                case "java.time.OffsetDateTime":
                    res = ((OffsetDateTime)t).isAfter((OffsetDateTime)tInicio) & ((OffsetDateTime)t).isBefore((OffsetDateTime)tFim);
                    break;
                case "java.time.OffsetTime":
                    res = ((OffsetTime)t).isAfter((OffsetTime)tInicio) & ((OffsetTime)t).isBefore((OffsetTime)tFim);
                    break;
                case "java.time.Instant":
                    res = ((Instant)t).isAfter((Instant)tInicio) & ((Instant)t).isBefore((Instant)tFim);
                    break;
                case "java.time.Year":
                    res = ((Year)t).isAfter((Year)tInicio) & ((Year)t).isBefore((Year)tFim);
                    break;
                case "java.time.YearMonth":
                    res = ((YearMonth)t).isAfter((YearMonth)tInicio) & ((YearMonth)t).isBefore((YearMonth)tFim);
                    break;
                default:
                    throw new UnsupportedTemporalTypeException("Tipo não suportado.");

            }

            return res;
        }
        else throw new UnsupportedTemporalTypeException("Tipos do argumentos não são iguais.");
    }

    //Verifica a sobreposição de intrevalos
    public static boolean isInBetween(Temporal ltInicial, Temporal ltFinal, Temporal inicio, Temporal fim){
        return isBetween(inicio, ltInicial, ltFinal) || isBetween(fim, ltInicial, ltFinal) || isBetween(ltInicial, inicio, fim) || isBetween(ltFinal, inicio, fim);
    }

    //Verifica a sobreposicao de intervalos, ao longo de um periodo
    public static boolean conflitoPeriodo(LocalDateTime ltInicial, LocalDateTime  ltFinal, LocalDateTime  inicio, LocalDateTime  fim, int periodo, LocalDateTime  limite){
        boolean r = false;

        LocalDateTime  currentI = inicio;
        LocalDateTime  currentF = fim;

        r = betweenOrEq(ltInicial, ltFinal, currentI, currentF) ;

        if(periodo > 0){
            while(!r && currentI.isBefore(limite)){
                currentI = currentI.plusDays(periodo);
                currentF = currentF.plusDays(periodo);
                r = betweenOrEq(ltInicial, ltFinal, currentI, currentF);
            }

        }


        return r;

    }

    //Verifica sobreposição entre intervalos (iguais inclusive)
    private static boolean betweenOrEq(Temporal ltInicial, Temporal ltFinal, Temporal inicio, Temporal fim){
        return ltInicial.equals(inicio) || ltFinal.equals(fim) || isInBetween(ltInicial, ltFinal, inicio, fim);
    }

    //Verifica se os dois momentos diferem em menos de uma hora
    public static boolean menos1Hora(LocalDateTime pedido, LocalDateTime agendamento){

        if(pedido.isBefore(agendamento))
            return ChronoUnit.MINUTES.between(pedido, agendamento) < 60;

        return false;
    }


    /**
     * Atualiza a informação de um evento
     * @param e
     * @param a
     */
    public static void updateEvento(Evento e, Alteracao a){
        e.setNome(a.getNome());
        e.setDescricao(a.getDescricao());
        e.setDateTimeInicial(a.getDateTimeInicial());
        e.setDateTimeFinal(a.getDateTimeFinal());
        e.setEspaco(a.getEspaco());
        e.setPeriodicidade(a.getPeriodicidade());
        e.setLimite(a.getLimite());
    }
}
