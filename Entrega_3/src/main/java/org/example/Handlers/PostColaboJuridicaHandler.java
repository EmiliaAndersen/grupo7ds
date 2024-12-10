package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Colaboraciones.Factory.DonacionDeDineroFactory;
import org.example.Dominio.Colaboraciones.Factory.HacerseCargoDeHeladeraFactory;
import org.example.Dominio.Colaboraciones.Factory.OfrecerProductosFactory;
import org.example.Dominio.Heladeras.Heladera;
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

import javax.persistence.EntityManager;

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
        try {
            switch (tipoColabo) {
                case "hc": {

                    String nombre = ctx.formParam("nombre");
                 

                    String longitudParam = ctx.formParam("longitud");
                    String latitudParam = ctx.formParam("latitud");
                    String direccion = ctx.formParam("direccion");
                    String tempMinParam = ctx.formParam("temp_min");
                    String tempMaxParam = ctx.formParam("temp_max");


                    double longitud = Double.parseDouble(longitudParam);
                    double latitud = Double.parseDouble(latitudParam);
                    int tempMin = Integer.parseInt(tempMinParam);
                    int tempMax = Integer.parseInt(tempMaxParam);
                    
                    EntityManager em = BDUtils.getEntityManager();
                    BDUtils.comenzarTransaccion(em);

                    try{
                    PuntoEstrategico punto = new PuntoEstrategico(nombre, longitud, latitud, direccion);


                    Heladera heladera = new Heladera(tempMax, tempMin, punto);
                    HacerseCargoDeHeladeraFactory factoryHC = new HacerseCargoDeHeladeraFactory();
                    Colaboracion hacerseCargoHeladera = factoryHC.crearColaboracion(heladera,punto,LocalDate.now());
                    hacerseCargoHeladera.setColaborador(colaborador);
                    repoColaboraciones.addHacerseCargoHeladera(hacerseCargoHeladera, punto, heladera);

                    Double puntosSumados = hacerseCargoHeladera.calcularPuntos();
                    
                    colaborador.setPuntos(colaborador.getPuntos() + puntosSumados);

                    em.merge(colaborador);
                    BDUtils.commit(em);

                    }
                    catch(Exception e){
                         model.put("errorMessage", "Error" + e.getMessage());
                        ctx.render("/templates/colaboracionJuridica.mustache", model);
                        BDUtils.rollback(em);
                    }

                    break;

                }
                case "op": {
                    String tipo_producto = ctx.formParam("tipo-producto");
                    String marca = ctx.formParam("marca");
                    int monto = Integer.parseInt(ctx.formParam("monto"));

                    OfrecerProductosFactory factoryOP = new OfrecerProductosFactory();
                    Colaboracion ofrecerProductos = factoryOP.crearColaboracion(tipo_producto, marca, monto);
                    ofrecerProductos.setColaborador(colaborador);
                    repoColaboraciones.addOfrecerProducto(ofrecerProductos);

                    break;
                }
                case "dd": {
                    LocalDate fecha = LocalDate.parse(ctx.formParam("fecha_nacimiento"));
                    double monto = Double.parseDouble(ctx.formParam("password"));
                    String frecuencia = ctx.formParam("frecuencia");


                    // TODO: Agregar un atributo session para obtener el colaborador asociado al usuario que realiza la colaboracion para linkearlo al repo
                   EntityManager em = BDUtils.getEntityManager();
                    BDUtils.comenzarTransaccion(em);
                    try{
                    DonacionDeDineroFactory factoryDD = new DonacionDeDineroFactory();
                    Colaboracion donacionDinero = factoryDD.crearColaboracion(fecha, monto, frecuencia);
                    donacionDinero.setColaborador(colaborador);

                    Double puntosSumados = donacionDinero.calcularPuntos();
                    
                    colaborador.setPuntos(colaborador.getPuntos() + puntosSumados);

                    repoColaboraciones.addDonacionDinero(donacionDinero);
                    em.merge(colaborador);
                    BDUtils.commit(em);

                    }
                    catch(Exception e){
                         model.put("errorMessage", "Error");
                        ctx.render("/templates/colaboracionJuridica.mustache", model);
                        BDUtils.rollback(em);
                    }
                    break;
                }
            }
            model.put("successMessage", "Colaboracion creada exitosamente");
        }catch (Exception e){
            model.put("errorMessage","Error al crear la colaboracion");
        }
        System.out.println("Colaboracion creada");
        ctx.render("/templates/colaboracionJuridica.mustache",model);
    }
}
