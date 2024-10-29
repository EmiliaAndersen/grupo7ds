package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.Dominio.Colaboraciones.Factory.DonacionDeDineroFactory;
import org.example.Dominio.Colaboraciones.Factory.HacerseCargoDeHeladeraFactory;
import org.example.Dominio.Colaboraciones.Factory.OfrecerProductosFactory;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
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

        String tipoColabo = ctx.formParam("tipo_colaborador");
        Map<String, Object> model = new HashMap<>();

        switch (tipoColabo){
            case "hc":{
                String heladera_id = ctx.formParam("heladera");
                String ubicacion_desc = ctx.formParam("ubicacion");

                // TODO: falta ver como obtener los datos que estan en null
                PuntoEstrategico ubicacion = new PuntoEstrategico(ubicacion_desc, null, null, null);
                HacerseCargoDeHeladeraFactory factoryHC = new HacerseCargoDeHeladeraFactory();
                // TODO: Repensar las cosas que necesita este tipo de colaboración para ser creado. No tiene mucho sentido lo actual
                // Me parece que tiene mas sentido que solamente reciba una ubicacion.
                factoryHC.crearColaboracion(null, null, ubicacion, null);
                break;
            }
            case "op":{
                String tipo_producto = ctx.formParam("tipo-producto");
                String marca = ctx.formParam("marca");
                int monto = Integer.parseInt(ctx.formParam("monto"));

                OfrecerProductosFactory factoryOP = new OfrecerProductosFactory();
                factoryOP.crearColaboracion(null, tipo_producto, marca, monto);
                break;
            }
            case "dd":{
                LocalDate fecha = LocalDate.parse(ctx.formParam("fecha_nacimiento"));
                double monto = Double.parseDouble(ctx.formParam("password"));
                String frecuencia = ctx.formParam("frecuencia");

                DonacionDeDineroFactory factoryDD = new DonacionDeDineroFactory();
                // TODO: Agregar un atributo session para obtener el colaborador asociado al usuario que realiza la colaboracion
                factoryDD.crearColaboracion(null, fecha, monto, frecuencia);
                break;
            }
        }

        System.out.println("Colaboracion creada");
        ctx.redirect("/perfil");
    }
}
