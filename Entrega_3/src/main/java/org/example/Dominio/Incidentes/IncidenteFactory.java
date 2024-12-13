package org.example.Dominio.Incidentes;

import org.example.BDUtils;
import org.example.Dominio.Heladeras.EstadoHeladera;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Reportes.GeneradorDeReportes;
import org.example.Dominio.Rol.Colaborador;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IncidenteFactory {
    EntityManager em = BDUtils.getEntityManager();

    private List<GeneradorDeReportes> suscriptores = new ArrayList<GeneradorDeReportes>();

    public Incidente crearAlerta(TipoAlerta tipo, Heladera heladera){
        this.notificarReporte();
        return new Alerta(tipo, heladera, LocalDateTime.now());
    }

    public Incidente crearFalla(Colaborador colaborador, Heladera heladera, String descripcion){
        this.notificarReporte();
        BDUtils.comenzarTransaccion(em);
        heladera = em.merge(heladera);
        heladera.setEstado(EstadoHeladera.INACTIVA);
        em.persist(heladera);
        BDUtils.commit(em);
        return new FallaTecnica(colaborador, heladera, LocalDateTime.now(), descripcion);
    }

    private void notificarReporte(){
//        for(GeneradorDeReportes reporte : suscriptores){
//            reporte.update();
//        }
    }

    public void agregarSuscriptor(GeneradorDeReportes reporte){
        suscriptores.add(reporte);
    }
}
