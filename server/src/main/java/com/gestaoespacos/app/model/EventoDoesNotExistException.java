package com.gestaoespacos.app.model;

public class EventoDoesNotExistException extends Exception{

    public EventoDoesNotExistException(){
        super();
    }

    public EventoDoesNotExistException(String msg){
        super(msg);
    }
}
