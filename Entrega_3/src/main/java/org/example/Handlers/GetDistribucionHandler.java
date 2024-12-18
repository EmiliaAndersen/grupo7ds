package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.Dominio.Heladeras.Heladera;
import org.example.repositorios.RepositorioHeladeras;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class GetDistribucionHandler implements Handler {
  @Override
  public void handle(@NotNull Context context) throws Exception {
    var model = new HashMap<String, Object>();
    String cantidadViandas = context.queryParam("viandas");
    String heladeraId = context.queryParam("heladera_id");
    RepositorioHeladeras repositorioHeladeras = RepositorioHeladeras.getInstance();

    if (cantidadViandas != null && heladeraId != null) {
      List<Heladera> heladeras = repositorioHeladeras.obtenerHeladerasDisponibles(Integer.parseInt(cantidadViandas));
      heladeras.stream()
          .filter(heladera -> heladera.getId() != Long.parseLong(heladeraId)) // Filtrar por ID
          .collect(Collectors.toList());
      if (heladeras.isEmpty()){
        model.put("ErrorMotivo","No hay heladeras disponibles");
        context.render("/templates/distribucion.mustache",model);

      }else {
        model.put("heladeras",heladeras);
        context.render("/templates/distribucion.mustache",model);
      }
    }else{
      model.put("ErrorMotivo","error");
      context.render("/templates/distribucion.mustache",model);
    }
  }
}
