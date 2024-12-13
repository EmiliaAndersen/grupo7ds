package org.example.Migrador;

import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.*;
import org.example.Dominio.Colaboraciones.Factory.DistribucionDeViandasFactory;
import org.example.Dominio.Colaboraciones.Factory.DonacionDeDineroFactory;
import org.example.Dominio.Colaboraciones.Factory.DonacionDeViandaFactory;
import org.example.Dominio.Colaboraciones.Factory.RegistrarPersonasFactory;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.Colaborador;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class ServiciosColaboracion {

    public void asignarColaboracion(Colaborador colaborador, String formaColaboracion, LocalDate fechaColaboracion, double cantidad, String mail) {
        if (colaborador == null) {
            // Manejar el caso cuando colaborador es null, por ejemplo, lanzar una excepción o registrar un mensaje de error
            System.err.println("El colaborador es null. No se puede asignar la colaboración.");
            return;
        }

        if (formaColaboracion.equals("DINERO")) {
            DonacionDeDineroFactory factoryDD = new DonacionDeDineroFactory();
            Colaboracion colaboracion = factoryDD.crearColaboracion(fechaColaboracion, cantidad,"SinFrecuencia");

            colaborador.agregarColaboracion(colaboracion);
            colaboracion.setColaborador(colaborador);

            EntityManager em = BDUtils.getEntityManager();
            BDUtils.comenzarTransaccion(em);
            em.persist(colaboracion);
            BDUtils.commit(em);
        }
        else{
            generarColaboraciones(formaColaboracion, fechaColaboracion, cantidad, colaborador);
        }
    }


    static void generarColaborador(PersonaHumana persona, Colaborador colaborador) {
        colaborador.setPersona(persona);
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        em.persist(colaborador);
        BDUtils.commit(em);
    }

    Colaborador buscarColaboradorPorPersona(List<Colaborador> colaboradores, PersonaHumana persona) {
        for (Colaborador colaborador : colaboradores) {
            if(colaborador.getPersona() == null){
                return null;
            }
            else if (colaborador.getPersona().equals(persona)) {
                return colaborador;
            }
        }
        return null; // Retorna null si no se encuentra ningún colaborador para la persona
    }


    private void generarColaboraciones(String formaColaboracion, LocalDate fechaColaboracion, double cantidad, Colaborador colaborador) {
        switch (formaColaboracion) {
            case "DONACION_VIANDAS":
                DonacionDeViandaFactory factoryDV = new DonacionDeViandaFactory();
                for(int i = 0; i < cantidad; i++){
                    Colaboracion colaboracion = factoryDV.crearColaboracion(null,null);

                    colaborador.agregarColaboracion(colaboracion);
                    colaboracion.setColaborador(colaborador);

                    EntityManager em = BDUtils.getEntityManager();
                    BDUtils.comenzarTransaccion(em);
                    em.persist(colaboracion);
                    BDUtils.commit(em);
                }
                break;
            case "REDISTRIBUCION_VIANDAS":
                DistribucionDeViandasFactory factoryDDV = new DistribucionDeViandasFactory();
                for(int i = 0; i < cantidad; i++){
                    Colaboracion colaboracion = factoryDDV.crearColaboracion(null, null,null, "Sin motivo", fechaColaboracion);

                    colaborador.agregarColaboracion(colaboracion);
                    colaboracion.setColaborador(colaborador);

                    EntityManager em = BDUtils.getEntityManager();
                    BDUtils.comenzarTransaccion(em);
                    em.persist(colaboracion);
                    BDUtils.commit(em);
                }
                break;
            case "ENTREGA_TARJETAS":
                RegistrarPersonasFactory factoryPV = new RegistrarPersonasFactory();
                for(int i = 0; i < cantidad; i++){
                    Colaboracion colaboracion = factoryPV.crearColaboracion();

                    colaborador.agregarColaboracion(colaboracion);
                    colaboracion.setColaborador(colaborador);

                    EntityManager em = BDUtils.getEntityManager();
                    BDUtils.comenzarTransaccion(em);
                    em.persist(colaboracion);
                    BDUtils.commit(em);
                }
                break;
            default:
        }
    }
}
