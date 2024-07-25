package org.example.Migrador;

import org.example.Dominio.Colaboraciones.*;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.Colaborador;

import java.time.LocalDate;
import java.util.List;

public class ServiciosColaboracion {

    public void asignarColaboracion(Colaborador colaborador, String formaColaboracion, LocalDate fechaColaboracion, double cantidad, String mail) {
        if (colaborador == null) {
            // Manejar el caso cuando colaborador es null, por ejemplo, lanzar una excepción o registrar un mensaje de error
            System.err.println("El colaborador es null. No se puede asignar la colaboración.");
            return;
        }
        Colaboracion colaboracion = generarColaboracion(formaColaboracion, fechaColaboracion, cantidad);
        if (colaboracion == null) return;
        colaborador.agregarColaboracion(colaboracion);
    }


    static void generarColaborador(PersonaHumana persona, Colaborador colaborador) {
        colaborador.setPersona(persona);
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


    private Colaboracion generarColaboracion(String formaColaboracion, LocalDate fechaColaboracion, double cantidad) {
        Colaboracion colaboracion;
        switch (formaColaboracion) {
            case "DINERO":
                colaboracion = new DonacionDeDinero(fechaColaboracion, cantidad, null);
                break;
            case "DONACION_VIANDAS":
                colaboracion = new DonacionDeVianda();
                break;
            case "REDISTRIBUCION_VIANDAS":
                colaboracion = new DistribucionDeViandas(null, null, null, fechaColaboracion);
                break;
            case "ENTREGA_TARJETAS":
                colaboracion = new RegistrarPersonasEnSituacionVulnerable();
                break;
            default:
                return null;
        }
        return colaboracion;
    }
}
