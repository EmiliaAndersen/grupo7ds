package org.example.Dominio.Colaboraciones.Factory;

import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Colaboraciones.DonacionDeVianda;
import org.example.Dominio.Colaboraciones.TipoColaborador;
import org.example.Dominio.Reportes.GeneradorReporteCantidadViandasColaborador;
import org.example.Dominio.Reportes.GeneradorReporteViandasColocadas;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Viandas.Vianda;

import java.util.List;

public class DonacionDeViandaFactory extends ColaboracionFactory{

    public Colaboracion crearColaboracion(Colaborador colaborador, List<Vianda> viandas){
        if(!this.validarTipoColaborador(colaborador, TipoColaborador.P_HUMANA))
        {
            return null;
        }

        this.notificarReportes();
        return new DonacionDeVianda(viandas);
    }

    private void notificarReportes(){
        GeneradorReporteViandasColocadas reportes1 = GeneradorReporteViandasColocadas.getInstance();
        reportes1.update();

        GeneradorReporteCantidadViandasColaborador reportes2 = GeneradorReporteCantidadViandasColaborador.getInstance();
        reportes2.update();
    }
}
