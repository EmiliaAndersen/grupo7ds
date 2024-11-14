package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Colaboraciones.Factory.DonacionDeDineroFactory;
import org.example.Dominio.Colaboraciones.Factory.HacerseCargoDeHeladeraFactory;
import org.example.Dominio.Colaboraciones.Factory.OfrecerProductosFactory;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Rol.Colaborador;
import org.example.repositorios.RepositorioColaboraciones;
import org.example.repositorios.RepositorioColaboradores;
import org.example.repositorios.RepositorioHeladeras;
import org.example.repositorios.RepositorioUsuarios;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PostColaboJuridicaHandler implements Handler {

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

        switch (tipoColabo){
            case "hc":{
                String heladera_id = ctx.formParam("heladera");
                String ubicacion_desc = ctx.formParam("ubicacion");

                // TODO: falta ver como obtener los datos que estan en null
                PuntoEstrategico ubicacion = new PuntoEstrategico(ubicacion_desc, null, null, null);
                HacerseCargoDeHeladeraFactory factoryHC = new HacerseCargoDeHeladeraFactory();
                // TODO: Repensar las cosas que necesita este tipo de colaboración para ser creado. No tiene mucho sentido lo actual
                // Me parece que tiene mas sentido que solamente reciba una ubicacion.
                Colaboracion hacerseCargoHeladera = factoryHC.crearColaboracion( null, ubicacion, null);
                hacerseCargoHeladera.setColaborador(colaborador);
                repoColaboraciones.addHacerseCargoHeladera(hacerseCargoHeladera, ubicacion);
                break;
            }
            case "op":{
                String tipo_producto = ctx.formParam("tipo-producto");
                String marca = ctx.formParam("marca");
                int monto = Integer.parseInt(ctx.formParam("monto"));

                OfrecerProductosFactory factoryOP = new OfrecerProductosFactory();
                Colaboracion ofrecerProductos = factoryOP.crearColaboracion( tipo_producto, marca, monto);
                ofrecerProductos.setColaborador(colaborador);
                repoColaboraciones.addOfrecerProducto(ofrecerProductos);

                break;
            }
            case "dd":{
                LocalDate fecha = LocalDate.parse(ctx.formParam("fecha_nacimiento"));
                double monto = Double.parseDouble(ctx.formParam("password"));
                String frecuencia = ctx.formParam("frecuencia");


                // TODO: Agregar un atributo session para obtener el colaborador asociado al usuario que realiza la colaboracion para linkearlo al repo
                DonacionDeDineroFactory factoryDD = new DonacionDeDineroFactory();
                Colaboracion donacionDinero = factoryDD.crearColaboracion(fecha, monto, frecuencia);
                donacionDinero.setColaborador(colaborador);
                repoColaboraciones.addDonacionDinero(donacionDinero);
                break;
            }
        }

        System.out.println("Colaboracion creada");
        ctx.redirect("/perfil_persona_juridica");
    }
}
