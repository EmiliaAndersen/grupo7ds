package org.example.Handlers;

import javax.persistence.EntityManager;

import org.example.BDUtils;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Suscripciones.Suscriptor;
import org.example.Dominio.Suscripciones.TipoSuscripcion;
import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PostSuscripcion implements @NotNull Handler {
      public void handle(@NotNull Context context){
      EntityManager em = BDUtils.getEntityManager();
      BDUtils.comenzarTransaccion(em);
      
      Suscriptor sr = new Suscriptor();

      sr.setColaborador(null); //QUE VEA EL USUARIO

      String heladeraId = context.formParam("heladera");
      Heladera heladera = em.find(Heladera.class, heladeraId);
      sr.setHeladera(heladera);

      String num = context.formParam("motivo");
      sr.setNumeroAviso(stringToInt(context.formParam("motivo")));

      if(num.equals("1")){
         sr.setTipo(TipoSuscripcion.RESTANTES);
      }
      if(num.equals("2")){
         sr.setTipo(TipoSuscripcion.FALTANTES);
      }
      if(num.equals("3")){
         sr.setTipo(TipoSuscripcion.DESPERFECTO);
      }


      em.persist(sr);
      BDUtils.commit(em);
   
      context.redirect("/perfil");
     }

   public static int stringToInt(String str) {
        return Integer.parseInt(str);
   }
}
