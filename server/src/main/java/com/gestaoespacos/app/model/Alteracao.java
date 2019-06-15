package com.gestaoespacos.app.model;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("U")
public class Alteracao extends Pedido {
}
