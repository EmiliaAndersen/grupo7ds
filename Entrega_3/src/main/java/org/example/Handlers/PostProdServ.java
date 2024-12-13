package org.example.Handlers;

import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.OfrecerProductos;
import org.example.Dominio.Ofertas.Oferta;
import org.example.Dominio.Ofertas.TipoRubro;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.Colaborador;
import org.example.repositorios.RepositorioColaboradores;
import org.jetbrains.annotations.NotNull;
import org.example.Dominio.Colaboraciones.OfrecerProductos;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class PostProdServ implements @NotNull Handler {

    public void handle(@NotNull Context ctx){

    String producto = ctx.pathParam("id");
    long productoId = Long.parseLong(producto);

    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    try {

        RepositorioColaboradores repoColaboradores = RepositorioColaboradores.getInstance();
        String username = ctx.sessionAttribute("username");
        Colaborador colaborador = repoColaboradores.obtenerColaborador(username);
        

        Oferta oferta = em.createQuery(
          "SELECT p FROM Oferta p WHERE p.id = :id", Oferta.class)
          .setParameter("id", productoId)
          .getSingleResult();


        if (colaborador.getPuntos() >= oferta.getPuntosNecesarios()) {
          colaborador.setPuntos(colaborador.getPuntos() - oferta.getPuntosNecesarios());
          em.merge(colaborador);   
        }

        BDUtils.commit(em);

    } catch (Exception e) {
      BDUtils.rollback(em);
      

    }
    ctx.redirect("/prodserv");


  }

}
