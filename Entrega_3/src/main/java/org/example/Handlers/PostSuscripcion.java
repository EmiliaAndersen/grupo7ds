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

     if (colab == null) {
           context.status(404).result("El colaborador con el nombre '" + nombre + "' no existe.");
           return ;
        }

      sr.setColaborador(colab); 

      String heladeraId = context.formParam("heladera");
      Long helidlong;
      try {
          helidlong = Long.parseLong(heladeraId);
      } catch (NumberFormatException e) {
          context.status(400).result("El ID de la heladera no es válido.");
          return;
      }


      TypedQuery<Heladera> query = em.createQuery("SELECT h from Heladera h where h.id = :id ",Heladera.class);
      query.setParameter("id",helidlong);
      Heladera heladera;
      try{
        heladera = query.getSingleResult();
      }
       catch (NoResultException e) {
                throw new RuntimeException("Heladera no encontrada con ID: " + heladeraId, e);
            }
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
             context.status(400).result("El motivo no es válido.");
             return;
     }


      em.persist(sr);
      BDUtils.commit(em);
      String tipo = context.sessionAttribute("tipo_persona");
      context.redirect("/perfil_"+tipo);
     } catch (Exception e) {
            BDUtils.rollback(em);
            context.status(500).result("Se produjo un error: " + e.getMessage());
     }
   }
   public static int stringToInt(String str) {
        return Integer.parseInt(str);
   }

}
