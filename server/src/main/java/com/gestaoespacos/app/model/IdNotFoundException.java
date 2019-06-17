package com.gestaoespacos.app.model;

public class IdNotFoundException extends Exception{
    public IdNotFoundException(){
        super();
    }

    public IdNotFoundException(String msg){
        super(msg);
    }
}
