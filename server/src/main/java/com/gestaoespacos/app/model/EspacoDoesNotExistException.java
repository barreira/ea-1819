package com.gestaoespacos.app.model;

public class EspacoDoesNotExistException extends Exception{

    public EspacoDoesNotExistException(){
        super();
    }

    public EspacoDoesNotExistException(String msg){
        super(msg);
    }
}
