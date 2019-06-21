package com.gestaoespacos.app.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("A")
public class Alocacao extends Pedido{

    public Alocacao(){
        super();
    }

    public Alocacao(String nome, LocalDateTime inicio, LocalDateTime fim, String descricao, int periodo, LocalDateTime limite, Espaco e) {
        super(nome, inicio, fim, descricao, periodo, limite, e);
    }
}
