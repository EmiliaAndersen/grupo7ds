package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Colaboraciones.DonacionDeDinero;
import org.example.Dominio.Colaboraciones.DonacionDeVianda;
import org.example.Dominio.Colaboraciones.Factory.DistribucionDeViandasFactory;
import org.example.Dominio.Colaboraciones.Factory.DonacionDeDineroFactory;
import org.example.Dominio.Colaboraciones.Factory.DonacionDeViandaFactory;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Viandas.EstadoVianda;
import org.example.Dominio.Viandas.Vianda;
import org.example.repositorios.RepositorioColaboraciones;
import org.example.repositorios.RepositorioColaboradores;
import org.example.repositorios.RepositorioHeladeras;
import org.example.repositorios.RepositorioUsuarios;
import org.jetbrains.annotations.NotNull;

import javax.management.modelmbean.InvalidTargetObjectTypeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PostColaboHumanaHandler implements Handler {

    public void handle(@NotNull Context ctx) {

        RepositorioUsuarios repositorioUsuarios = RepositorioUsuarios.getRepositorioUsuarios();
        RepositorioColaboradores repoColaboradores = RepositorioColaboradores.getInstance();
        RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstance();
        RepositorioColaboraciones repoColaboraciones = RepositorioColaboraciones.getInstance();


        String tipoColabo = ctx.formParam("btn-colab");

        Map<String, Object> model = new HashMap<>();

        String userName = ctx.sessionAttribute("username");
        Colaborador colaborador = repoColaboradores.obtenerColaborador(userName);
        if (colaborador == null) {
            model.put("errorMessage", "El colaborador no existe");
            ctx.render("/templates/colaboracionHumana.mustache", model);
            return;
        }

        switch (Objects.requireNonNull(tipoColabo)) {
            case "dv": {
                String comida = ctx.formParam("Comida");
                LocalDate fecha_caducidad = LocalDate.parse(ctx.formParam("fecha-caducidad"));
                LocalDate fecha_donacion_vianda = LocalDate.parse(ctx.formParam("fechaDonacionVianda"));
                String nombreColaborador = ctx.formParam("colaborador");
                String heladera_id = ctx.formParam("heladera");
                float peso = Float.parseFloat(ctx.formParam("peso"));
                float calorias = Float.parseFloat(ctx.formParam("calorias"));

                Heladera heladera = repoHeladeras.obtenerHeladera(heladera_id);
                if (heladera == null) {
                    model.put("errorMessage", "La heladera no existe");
                    ctx.render("/templates/colaboracionHumana.mustache", model);
                    return;
                }

                Vianda vianda = new Vianda(comida, fecha_caducidad, fecha_donacion_vianda, heladera, calorias, peso, EstadoVianda.ENTREGADA);
                DonacionDeViandaFactory factoryDV = new DonacionDeViandaFactory();
                Colaboracion donacionDeVianda = factoryDV.crearColaboracion(vianda);
                donacionDeVianda.setColaborador(colaborador);

                repoColaboraciones.addDonacionVianda(donacionDeVianda, vianda);
                break;
            }
            case "dd": {
                LocalDate fecha = LocalDate.parse(ctx.formParam("fecha_nacimiento"));
                double monto = Double.parseDouble(ctx.formParam("password"));
                String frecuencia = ctx.formParam("frecuencia");


                DonacionDeDineroFactory factoryDD = new DonacionDeDineroFactory();
                Colaboracion donacionDinero = factoryDD.crearColaboracion(fecha, monto, frecuencia);
                donacionDinero.setColaborador(colaborador);
                repoColaboraciones.addDonacionDinero(donacionDinero);
                break;
            }
            case "ddv": {

                String heladera_origen_id = ctx.formParam("heladera-origen");
                String heladera_destino_id = ctx.formParam("heladera-destino");
                // TODO: esto me parece que no hace falta. Hay que verlo.
                int cantidad = Integer.parseInt(ctx.formParam("cantidad"));
                String motivo = ctx.formParam("motivos");


                Heladera heladera_origen = repoHeladeras.obtenerHeladera(heladera_origen_id);
                Heladera heladera_destino = repoHeladeras.obtenerHeladera(heladera_destino_id);

                if (heladera_origen == null || heladera_destino == null) {
                    model.put("errorMessage", "Alguna de las heladeras no existe.");
                    ctx.render("/templatescolaboracionHumana.mustache", model);
                }

                DistribucionDeViandasFactory factoryDDV = new DistribucionDeViandasFactory();
                Colaboracion distribucionVianda = factoryDDV.crearColaboracion( heladera_origen, heladera_destino, motivo, LocalDate.now());
                distribucionVianda.setColaborador(colaborador);
                repoColaboraciones.addDistribucionVianda(distribucionVianda);
                break;
            }
        }

        System.out.println("Colaboracion creada");
        ctx.redirect("/perfil_persona_humana");

    }
}
