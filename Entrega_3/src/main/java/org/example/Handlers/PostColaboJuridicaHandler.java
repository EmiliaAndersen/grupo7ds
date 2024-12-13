package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Colaboraciones.Factory.DonacionDeDineroFactory;
import org.example.Dominio.Colaboraciones.Factory.HacerseCargoDeHeladeraFactory;
import org.example.Dominio.Colaboraciones.Factory.OfrecerProductosFactory;
import org.example.Dominio.Colaboraciones.HacerseCargoDeHeladera;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityManager;

public class PostColaboJuridicaHandler implements Handler {

    public void handle(@NotNull Context ctx) {
        RepositorioUsuarios repositorioUsuarios = RepositorioUsuarios.getRepositorioUsuarios();
        RepositorioColaboradores repoColaboradores = RepositorioColaboradores.getInstance();
        RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstance();
        RepositorioColaboraciones repoColaboraciones = RepositorioColaboraciones.getInstance();
        Heladera heladera;
        HacerseCargoDeHeladeraFactory factoryHC = new HacerseCargoDeHeladeraFactory();

        EntityManager em;

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
                case "hc2":
                    String heladera_id = ctx.formParam("heladeraSelect");
                    heladera = repoHeladeras.obtenerHeladera(heladera_id);
                    if (heladera == null) {
                        model.put("errorMessage", "La heladera no existe");
                        ctx.render("/templates/colaboracionJuridica.mustache", model);
                        return;
                    }
                    List<HacerseCargoDeHeladera> colabs = repoColaboraciones.obtenerTodasHacerseCargoHeladera();
                    for(HacerseCargoDeHeladera colab : colabs){
                        if(colab.getHeladera().getId() == heladera.getId() && Objects.equals(colab.getColaborador().getId(), colaborador.getId())){
                            model.put("errorMessage", "Ya esta a cargo de la heladera seleccionada");
                            ctx.render("/templates/colaboracionJuridica.mustache", model);
                            return;
                        }
                    }
                    Colaboracion hacerseCargoHeladeraSeleccionada = factoryHC.crearColaboracion(heladera,heladera.getUbicacion(),LocalDate.now());
                    hacerseCargoHeladeraSeleccionada.setColaborador(colaborador);
                    repoColaboraciones.addHacerseCargoHeladeraSeleccionada(hacerseCargoHeladeraSeleccionada, heladera.getUbicacion(), heladera);

                    Double puntosSumados = hacerseCargoHeladeraSeleccionada.calcularPuntos();

                    colaborador.setPuntos(colaborador.getPuntos() + puntosSumados);

                    em = BDUtils.getEntityManager();
                    BDUtils.comenzarTransaccion(em);
                    em.merge(colaborador);
                    BDUtils.commit(em);

                    break;
                case "hc1":

                    String nombre = ctx.formParam("nombre");
                    String longitudParam = ctx.formParam("longitudInput");
                    String latitudParam = ctx.formParam("latitudInput");
                    String direccion = ctx.formParam("direccion");
                    String tempMinParam = ctx.formParam("temp_min");
                    String tempMaxParam = ctx.formParam("temp_max");
                    String capacidadViandas = ctx.formParam("capacidadViandas");


                    double longitud = Double.parseDouble(longitudParam);
                    double latitud = Double.parseDouble(latitudParam);
                    int tempMin = Integer.parseInt(tempMinParam);
                    int tempMax = Integer.parseInt(tempMaxParam);
                    int capacidad = Integer.parseInt(capacidadViandas);

                    em = BDUtils.getEntityManager();
                    BDUtils.comenzarTransaccion(em);

                    try{
                        PuntoEstrategico punto = new PuntoEstrategico(nombre, longitud, latitud, direccion);


                        heladera = new Heladera(tempMax, tempMin, punto,capacidad);
                        factoryHC = new HacerseCargoDeHeladeraFactory();
                        Colaboracion hacerseCargoHeladera = factoryHC.crearColaboracion(heladera,punto,LocalDate.now());
                        hacerseCargoHeladera.setColaborador(colaborador);
                        repoColaboraciones.addHacerseCargoHeladeraGenerada(hacerseCargoHeladera, punto, heladera);

                        Double puntosSumados2 = hacerseCargoHeladera.calcularPuntos();

                        colaborador.setPuntos(colaborador.getPuntos() + puntosSumados2);

                        em.merge(colaborador);
                        BDUtils.commit(em);

                    }
                    catch(Exception e){
                        model.put("errorMessage", "Error" + e.getMessage());
                        ctx.render("/templates/colaboracionJuridica.mustache", model);
                        BDUtils.rollback(em);
                    }
                    break;


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
                    em = BDUtils.getEntityManager();
                    BDUtils.comenzarTransaccion(em);
                    try{
                    DonacionDeDineroFactory factoryDD = new DonacionDeDineroFactory();
                    Colaboracion donacionDinero = factoryDD.crearColaboracion(fecha, monto, frecuencia);
                    donacionDinero.setColaborador(colaborador);

                    Double puntosSumados3 = donacionDinero.calcularPuntos();

                    colaborador.setPuntos(colaborador.getPuntos() + puntosSumados3);

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

