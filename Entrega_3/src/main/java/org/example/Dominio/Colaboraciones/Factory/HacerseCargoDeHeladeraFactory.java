package org.example.Dominio.Colaboraciones.Factory;

import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Colaboraciones.HacerseCargoDeHeladera;
import org.example.Dominio.Colaboraciones.TipoColaborador;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Rol.Colaborador;
import org.example.Servicio.LocalizacionEstrategicaAPI;

public class HacerseCargoDeHeladeraFactory extends ColaboracionFactory {
    public Colaboracion crearColaboracion(Colaborador colaborador, LocalizacionEstrategicaAPI api, PuntoEstrategico punto, Double radio) {
        if(!this.validarTipoColaborador(colaborador, TipoColaborador.P_JURIDICA)){
            return null;
        }

        // Podria notificarse a los reportes sobre la nueva heladera.
        return new HacerseCargoDeHeladera(api, punto, radio);
    }
}
