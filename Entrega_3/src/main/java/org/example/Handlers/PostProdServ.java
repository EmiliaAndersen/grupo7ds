package org.example.Handlers;

import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.OfrecerProductos;
import org.example.Dominio.Ofertas.Oferta;
import org.example.Dominio.Ofertas.TipoRubro;
import org.example.Dominio.Persona.PersonaHumana;
import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PostProdServ implements @NotNull Handler {

    public void handle(@NotNull Context context){


      context.redirect("/ofrecer");

  }
}
