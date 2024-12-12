package org.example.Dominio.Colaboraciones.Factory;

import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Colaboraciones.HacerseCargoDeHeladera;
import org.example.Dominio.Colaboraciones.TipoColaborador;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Rol.Colaborador;
import org.example.Servicio.LocalizacionEstrategicaAPI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HacerseCargoDeHeladeraFactory extends ColaboracionFactory {
    public Colaboracion crearColaboracion(LocalizacionEstrategicaAPI api, PuntoEstrategico punto, Double radio) {
//        if(!this.validarTipoColaborador(colaborador, TipoColaborador.P_JURIDICA)){
//            return null;
//        }

        // Podria notificarse a los reportes sobre la nueva heladera.
        return new HacerseCargoDeHeladera(api, punto, radio);
    }
    public Colaboracion crearColaboracion(Heladera heladera, PuntoEstrategico punto, LocalDate fecha) {
//        if(!this.validarTipoColaborador(colaborador, TipoColaborador.P_JURIDICA)){
//            return null;
//        }

        // Podria notificarse a los reportes sobre la nueva heladera.
        return new HacerseCargoDeHeladera(punto,heladera,fecha);
    }
}
