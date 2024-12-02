package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;

public class GetColaboracionesRealizadas implements Handler {

  @Override
  public void handle(@NotNull Context context) throws Exception {
    var model = new HashMap<String, Object>();
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    TypedQuery<Colaboracion> query = em.createQuery(
        "SELECT c FROM Colaboracion c JOIN c.colaborador co JOIN co.persona p JOIN p.usuario u where u.usuario = :usu"
    ,Colaboracion.class);
    String usuario = context.sessionAttribute("username");
    query.setParameter("usu",usuario);

    List<Colaboracion> colabs = query.getResultList();

    if(colabs.isEmpty()){
      model.put("colabs_vacio","No hay colaboraciones realizadas");
    }else{
      List<DistribucionDeViandas> distribuciones = colabs.stream()
          .filter(c -> c instanceof DistribucionDeViandas)
          .map(c -> (DistribucionDeViandas) c)
          .toList();

      List<DonacionDeDinero> donacionesDinero = colabs.stream()
          .filter(c -> c instanceof DonacionDeDinero)
          .map(c -> (DonacionDeDinero) c)
          .toList();

      List<DonacionDeVianda> donacionesVianda = colabs.stream()
          .filter(c -> c instanceof DonacionDeVianda)
          .map(c -> (DonacionDeVianda) c)
          .toList();

      List<HacerseCargoDeHeladera> hacerseCargo = colabs.stream()
          .filter(c -> c instanceof HacerseCargoDeHeladera)
          .map(c -> (HacerseCargoDeHeladera) c)
          .toList();

      List<OfrecerProductos> ofertasProductos = colabs.stream()
          .filter(c -> c instanceof OfrecerProductos)
          .map(c -> (OfrecerProductos) c)
          .toList();


      model.put("distribuciones",distribuciones);
      model.put("donacionesDinero",donacionesDinero);
      model.put("donacionesVianda",donacionesVianda);
      model.put("hacerseCargo",hacerseCargo);
      model.put("ofertasProductos",ofertasProductos);
    }



    context.render("/templates/colaboracionesRealizadas.mustache",model);
  }
}
