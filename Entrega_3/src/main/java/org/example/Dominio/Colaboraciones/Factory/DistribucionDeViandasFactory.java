package org.example.Dominio.Colaboraciones.Factory;

import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Colaboraciones.DistribucionDeViandas;
import org.example.Dominio.Colaboraciones.TipoColaborador;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Reportes.GeneradorDeReportes;
import org.example.Dominio.Reportes.GeneradorReporteViandasColocadas;
import org.example.Dominio.Rol.Colaborador;

import java.time.LocalDate;

public class DistribucionDeViandasFactory extends ColaboracionFactory {

    public Colaboracion crearColaboracion(Heladera heladeraOrigen, Heladera heladeraDestino, Double cant, String motivo, LocalDate fecha){
//        if(!this.validarTipoColaborador(TipoColaborador.P_HUMANA))
//        {
//            return null;
//        }

        return new DistribucionDeViandas(heladeraOrigen, heladeraDestino, cant, motivo, fecha);
    }
}
