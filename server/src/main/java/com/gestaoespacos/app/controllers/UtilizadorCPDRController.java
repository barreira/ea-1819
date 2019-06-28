package com.gestaoespacos.app.controllers;

import com.gestaoespacos.app.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usercpdr")
public class UtilizadorCPDRController {

    @PostMapping("/alocar")
    public void alocarEspaco(
            @RequestParam("id") long id_usercpdr,
            @RequestBody Alocacao a){

        try{
            GHE.alocarEspaco(id_usercpdr, a);
        }catch(IdNotFoundException e){ System.out.println(e);}

    }

    @PostMapping("/alterar")
    public void alocarEspaco(
            @RequestParam("id") long id_usercpdr,
            @RequestBody Alteracao a){

        try{
            GHE.alterarEvento(id_usercpdr, a);
        }catch(IdNotFoundException e){ System.out.println(e);}

    }

    @PostMapping("/pedidos/cancelar")
    public void cancelarPedido(
            @RequestParam("id") long id_usercpdr,
            @RequestParam("num") long nr_pedido){

        try{
            GHE.cancelarPedido(id_usercpdr, nr_pedido);
        }catch(IdNotFoundException e){ System.out.println(e);}

    }

    @GetMapping("/pedidos/pendentes")
    public List<Pedido> getPendentes(@RequestParam("id") long id_usercpdr){
        return GHE.getPendentes(id_usercpdr);
    }

    @GetMapping("/pedidos/atendidos")
    public List<Pedido> getAtendidos(@RequestParam("id") long id_usercpdr){
        return GHE.getAtendidos(id_usercpdr);
    }

    @PostMapping("/eventos/cancelar")
    public void cancelarEvento(
            @RequestParam("responsavel") long id_responsavel,
            @RequestParam("evento") long id_evt,
            @RequestParam(name = "justificacao", required = false) String justificacao){

        try{
            if(justificacao == null)
                GHE.cancelaEvento(id_responsavel, id_evt);
            else GHE.cancelaEvento(id_responsavel, id_evt, justificacao);
        }catch(IdNotFoundException e){ System.out.println(e);}

    }

    @GetMapping("/eventos/view")
    public List<Evento> getEventosResponsavel(@RequestParam("id") long id_responsavel){
        try{
            return GHE.eventosResp(id_responsavel);
        }catch(IdNotFoundException e){ System.out.println(e);}

        return null;
    }

}
