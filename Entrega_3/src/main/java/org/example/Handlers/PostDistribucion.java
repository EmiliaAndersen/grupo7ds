package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Colaboraciones.Factory.DistribucionDeViandasFactory;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Notificador.Notificador;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Suscripciones.TipoSuscripcion;
import org.example.Dominio.Viandas.Vianda;
import org.example.repositorios.RepositorioColaboraciones;
import org.example.repositorios.RepositorioColaboradores;
import org.example.repositorios.RepositorioHeladeras;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class PostDistribucion implements Handler {
  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);
    var model = new HashMap<String, Object>();
    RepositorioColaboradores repoColaboradores = RepositorioColaboradores.getInstance();
    RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstance();
    RepositorioColaboraciones repoColaboraciones = RepositorioColaboraciones.getInstance();
    Notificador notificador = Notificador.getInstance();

    String heladera_origen_id = ctx.queryParam("heladera_id");
    String heladera_destino_id = ctx.formParam("heladera");

    Double cantidad = Double.parseDouble(ctx.queryParam("viandas"));

    String userName = ctx.sessionAttribute("username");
    Colaborador colaborador = repoColaboradores.obtenerColaborador(userName);
    try{

      String motivo = ctx.formParam("motivos");

      if(cantidad == 0){
        model.put("errorMessage", "No puede distribuirse cero viandas");
        ctx.render("/templates/colaboracionHumana.mustache", model);
        return;
      }


      Heladera heladera_origen = repoHeladeras.obtenerHeladera(heladera_origen_id);
      Heladera heladera_destino = repoHeladeras.obtenerHeladera(heladera_destino_id);

      if (heladera_origen == null || heladera_destino == null) {
        model.put("errorMessage", "Alguna de las heladeras no existe.");
        ctx.render("/templatescolaboracionHumana.mustache", model);
        return;
      }

      Long heladeraOrigenId = Long.parseLong(heladera_origen_id);
      Long heladeraDestinoId = Long.parseLong(heladera_destino_id);

      List<Vianda> viandasEnHeladeraOrigen = em.createQuery("SELECT v FROM Vianda v WHERE v.heladera.id = :heladeraId", Vianda.class)
          .setParameter("heladeraId", heladeraOrigenId)
          .getResultList();


      if(viandasEnHeladeraOrigen == null){
        model.put("errorMessage", "No hay viandas en la heladera origen.");
        ctx.render("/templates/colaboracionHumana.mustache", model);
        return;
      }

      if(viandasEnHeladeraOrigen != null){
        if (viandasEnHeladeraOrigen.size() < cantidad) {
          model.put("errorMessage", "No hay suficiente viandas en la heladera origen.");
          ctx.render("/templates/colaboracionHumana.mustache", model);
          return;
        }
      }


      List<Vianda> viandasEnHeladeraDestino = em.createQuery("SELECT v FROM Vianda v WHERE v.heladera.id = :heladeraId", Vianda.class)
          .setParameter("heladeraId", heladeraDestinoId)
          .getResultList();

      if(viandasEnHeladeraDestino == null){
        if(heladera_destino.getCapacidad()< cantidad){
          model.put("errorMessage", "No hay suficiente capacidad en la heladera destino.");
          ctx.render("/templates/colaboracionHumana.mustache", model);
          return;
        }
      }

      if(viandasEnHeladeraDestino != null){
        if (heladera_destino.getCapacidad() - viandasEnHeladeraDestino.size() < cantidad) {
          model.put("errorMessage", "No hay suficiente capacidad en la heladera destino.");
          ctx.render("/templates/colaboracionHumana.mustache", model);
          return;
        }
      }

      List<Vianda> viandasSeleccionadas = viandasEnHeladeraOrigen.subList(0, cantidad.intValue());

      for (Vianda vianda : viandasSeleccionadas) {
        vianda.setHeladera(heladera_destino);
        heladera_origen.setStock(heladera_origen.getStock() -1);
        heladera_destino.setStock(heladera_destino.getStock() +1);
        em.merge(vianda);
      }

      em.merge(heladera_origen);
      notificador.movimientoHeladera(heladera_origen, TipoSuscripcion.RESTANTES);
      em.merge(heladera_destino);
      notificador.movimientoHeladera(heladera_destino,TipoSuscripcion.FALTANTES);
      DistribucionDeViandasFactory factoryDDV = new DistribucionDeViandasFactory();
      Colaboracion distribucionVianda = factoryDDV.crearColaboracion(heladera_origen, heladera_destino, cantidad ,"Distribucion de viandas", LocalDate.now());
      distribucionVianda.setColaborador(colaborador);
      repoColaboraciones.addDistribucionVianda(distribucionVianda);


      Double puntosSumados = distribucionVianda.calcularPuntos();

      colaborador.setPuntos(colaborador.getPuntos() + puntosSumados);

      em.merge(colaborador);

      BDUtils.commit(em);
      ctx.sessionAttribute("successMessage","Distribucion realizada correctamente");

      ctx.redirect("/front_page");

    }
    catch(Exception e){
      model.put("errorMessage", "Error" + e.getMessage());
      e.printStackTrace();
      ctx.redirect("/front_page");
      BDUtils.rollback(em);
    }
  }
}
