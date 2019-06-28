package com.gestaoespacos.app.controllers;

import com.gestaoespacos.app.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gestor")
public class GestorController {

    /*-----------------------------------------------------------------------------------------------------------------
     | Gerir Eventos
     -----------------------------------------------------------------------------------------------------------------
     */

    @PostMapping("/eventos/add")
    public void novoEvento(@RequestBody Evento evento) {
        try{
            GHE.novoEvento(evento);
        }catch(EspacoDoesNotExistException e){ System.out.println(e);}
    }

    @PostMapping("/eventos/update")
    public Evento updateEvento(
            @RequestParam("id") long id_evt,
            @RequestBody Evento novoEvento) {
        try{
            return GHE.updateEvento(id_evt, novoEvento);
        }catch(IdNotFoundException e){ System.out.println(e);}

        return null;
    }

    @PostMapping("/eventos/cancelar")
    public void cancelarEvento(
            @RequestParam("evento") long id_evt,
            @RequestParam(name = "justificacao", required = false) String justificacao){

        try{
            if(justificacao == null)
                GHE.cancelaEvento(id_evt);
            else GHE.cancelaEvento(id_evt, justificacao);
        }catch(IdNotFoundException e){ System.out.println(e);}

    }

    /*-----------------------------------------------------------------------------------------------------------------
     | Gerir Pedidos
     -----------------------------------------------------------------------------------------------------------------
     */

    @PostMapping("/pedidos/aceitar")
    public Pedido aceitaPedido(@RequestParam("nr") long nr_pedido){

        try{
            return GHE.aceitaPedido(nr_pedido);
        }catch(IdNotFoundException e){ System.out.println(e);}

        return null;
    }

    @PostMapping("/pedidos/rejeitar")
    public Pedido rejeitaPedido(
            @RequestParam("nr") long nr_pedido,
            @RequestParam(name = "justificacao", required = false) String justificacao){

        try{
            return justificacao == null ? GHE.rejeitaPedido(nr_pedido) : GHE.rejeitaPedido(nr_pedido, justificacao);
        }catch(IdNotFoundException e){ System.out.println(e);}

        return null;
    }

    /*-----------------------------------------------------------------------------------------------------------------
     | Gerir Espaços Comuns
     -----------------------------------------------------------------------------------------------------------------
     */

    @PostMapping("/ecs/add")
    public EspacoComum addEC(
            @RequestParam("designacao") String designacao,
            @RequestBody List<Espaco> espacos){

        return GHE.novoEC(designacao, espacos);
    }

    @PostMapping("/ecs/update")
    public EspacoComum updateEC(
            @RequestParam("id") long id_ec,
            @RequestParam("designacao") String designacao,
            @RequestBody List<Espaco> espacos){

        try{
            return GHE.updateEC(id_ec, designacao, espacos);
        }catch(IdNotFoundException e){ System.out.println(e);}

        return null;
    }

    @PostMapping("/ecs/delete")
    public EspacoComum deleteEC(@RequestParam("id") long id_ec){

        try{
            return GHE.removeEC(id_ec);
        }catch(IdNotFoundException e){ System.out.println(e);}

        return null;
    }

}
