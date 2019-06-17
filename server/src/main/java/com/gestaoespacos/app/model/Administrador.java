package com.gestaoespacos.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Administrador extends Ator {

    public Administrador(){
        super();
    }

    public void carregarAtores(String filename){
        //TODO
    }

    public void carregarEspacos(String filename){
        //TODO
    }

    public void carregarHorarios(String filename){
        //TODO
    }

}
