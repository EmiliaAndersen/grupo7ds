package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import org.example.Dominio.Rol.Colaborador;
import org.example.repositorios.RepositorioColaboradores;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;



public class OfProdHandler implements Handler {

    public void handle(@NotNull Context ctx) {
        var model = new HashMap<String, Object>();
        model.put("tipoPersona", ctx.sessionAttribute("tipo_persona"));


        boolean errorOfrecerProd = false;

        if(ctx.sessionAttribute("errorOfrecerProd  ") != null){
          errorOfrecerProd = ctx.sessionAttribute("errorOfrecerProd  ");
          if(errorOfrecerProd  ){
            model.put("errorMessageOfrecerProd ","Ocurrio un error");
            ctx.sessionAttribute("errorOfrecerProd  ",false);
          }
    
        }
        
          boolean successOfrecerProd = false;
          if(ctx.sessionAttribute("successOfrecerProd") != null){
              successOfrecerProd = ctx.sessionAttribute("successOfrecerProd");
              if(successOfrecerProd){
                model.put("successOfrecerProd","Se reporto correctamente");
                ctx.sessionAttribute("successOfrecerProd",false);
              }
          }
          
        ctx.render("/templates/ofrecer.mustache", model);
    }
}
