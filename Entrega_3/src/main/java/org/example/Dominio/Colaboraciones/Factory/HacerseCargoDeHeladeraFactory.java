package org.example.Dominio.Colaboraciones.Factory;

import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Colaboraciones.HacerseCargoDeHeladera;
import org.example.Dominio.Colaboraciones.TipoColaborador;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Rol.Colaborador;
import org.example.Servicio.LocalizacionEstrategicaAPI;

public class HacerseCargoDeHeladeraFactory extends ColaboracionFactory {
    public Colaboracion crearColaboracion(LocalizacionEstrategicaAPI api, PuntoEstrategico punto, Double radio) {
//        if(!this.validarTipoColaborador(colaborador, TipoColaborador.P_JURIDICA)){
//            return null;
//        }

        // Podria notificarse a los reportes sobre la nueva heladera.
        return new HacerseCargoDeHeladera(api, punto, radio);
    }

    public static boolean isWithinRadius(double lat1, double lon1, double lat2, double lon2, double radiusKm) {
        final double EARTH_RADIUS_KM = 6371.0;
        // Convertir latitudes y longitudes de grados a radianes
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Diferencias entre las coordenadas
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Fórmula de Haversine
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS_KM * c; // Distancia en kilómetros

        // Comparar la distancia con el radio
        return distance <= radiusKm;
    }
}


