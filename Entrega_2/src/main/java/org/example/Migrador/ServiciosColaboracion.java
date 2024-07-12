package org.example.Migrador;

import org.example.Dominio.Colaboraciones.*;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.Colaborador;

import java.time.LocalDate;
import java.util.List;

public class ServiciosColaboracion {

    public void asignarColaboracion(List<Colaborador> colaboradores, String formaColaboracion, LocalDate fechaColaboracion, double cantidad, PersonaHumana persona, String mail) {
        Colaboracion colaboracion = generarColaboracion(formaColaboracion, fechaColaboracion, cantidad);
        if (colaboracion == null) return;

        Colaborador colaborador = new Colaborador();
        colaborador.setPersona(persona);
        colaborador.agregarColaboracion(colaboracion);

        ServiciosMail.enviarMail(mail);

        colaboradores.add(colaborador);
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
