package org.example.Dominio.Incidentes;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Heladeras.EstadoHeladera;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Reportes.GeneradorReporteFallas;
import org.example.Servicio.LocalizadorTecnicos;
import org.example.Dominio.Rol.Tecnico;

import java.time.LocalDateTime;
import java.util.List;

public class Incidente {
    @Setter
    private Heladera heladera;
    @Setter
    private LocalDateTime fechaYHora;
    @Getter
    private List<Visita> visitas;

    public void registrarVisita(Visita visita){
        visitas.add(visita);
        if(visita.getPudoResolver())
        {
            heladera.setEstado(EstadoHeladera.ACTIVA);
        }
    }

    public void notificar(){
        Tecnico tecnicoCercano = LocalizadorTecnicos.getInstance().localizarTecnicoCercano(heladera.getUbicacion());
        // TODO: Notifica al tecnico
    }

    void notificarReporte(){
        GeneradorReporteFallas reporte = GeneradorReporteFallas.getInstance();
        reporte.update();
    }
}
