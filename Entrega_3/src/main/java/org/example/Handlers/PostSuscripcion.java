package org.example.Handlers;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.example.BDUtils;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Suscripciones.Suscriptor;
import org.example.Dominio.Suscripciones.TipoSuscripcion;
import org.example.repositorios.RepositorioColaboradores;
import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PostSuscripcion implements @NotNull Handler {
      public void handle(@NotNull Context context){
      EntityManager em = BDUtils.getEntityManager();
      BDUtils.comenzarTransaccion(em);
      
    try{
      Suscriptor sr = new Suscriptor();

      RepositorioColaboradores repositorioColaboradores = RepositorioColaboradores.getInstance();
      String nombre = context.sessionAttribute("username");
      Colaborador colab = repositorioColaboradores.obtenerColaborador(nombre);

      sr.setColaborador(colab); 

      String heladeraId = context.formParam("heladera");
      Long helidlong;
      helidlong = Long.parseLong(heladeraId);
       


      TypedQuery<Heladera> query = em.createQuery("SELECT h from Heladera h where h.id = :id ",Heladera.class);
      query.setParameter("id",helidlong);
      Heladera heladera;
      heladera = query.getSingleResult();

    
      sr.setHeladera(heladera);

      String num = context.formParam("motivo");
      sr.setNumeroAviso(stringToInt(context.formParam("motivo")));

 
      switch (stringToInt(num)) {
         case 1:
             sr.setTipo(TipoSuscripcion.RESTANTES);
             break;
         case 2:
             sr.setTipo(TipoSuscripcion.FALTANTES);
             break;
         case 3:
             sr.setTipo(TipoSuscripcion.DESPERFECTO);
             break;
         default:
         System.out.println("Motivo no valido: Ponga 1,2 o 3");
         context.sessionAttribute("ErrorMotivo", true);
         context.redirect("/suscripcion");
     }


      em.persist(sr);
      BDUtils.commit(em);
      String tipo = context.sessionAttribute("tipo_persona");
      context.redirect("/perfil_"+tipo);
     } catch (Exception e) {
            BDUtils.rollback(em);
     }
   }
   public static int stringToInt(String str) {
        return Integer.parseInt(str);
   }

}
