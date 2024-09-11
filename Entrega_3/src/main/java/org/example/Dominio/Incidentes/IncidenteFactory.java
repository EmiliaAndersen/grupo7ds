package org.example.Dominio.Incidentes;

import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Reportes.GeneradorDeReportes;
import org.example.Dominio.Rol.Colaborador;

import java.time.LocalDateTime;
import java.util.List;

public class IncidenteFactory {
    private List<GeneradorDeReportes> suscriptores;

    public Alerta crearAlerta(TipoAlerta tipo, Heladera heladera){
        this.notificarReporte();
        return new Alerta(tipo, heladera, LocalDateTime.now());
    }

    public FallaTecnica crearFalla(Colaborador colaborador, Heladera heladera){
        this.notificarReporte();
        return new FallaTecnica(colaborador, heladera, LocalDateTime.now());
    }

    private void notificarReporte(){
        for(GeneradorDeReportes reporte : suscriptores){
            reporte.update();
        }
    }

    public void agregarSuscriptor(GeneradorDeReportes reporte){
        suscriptores.add(reporte);
    }
}
