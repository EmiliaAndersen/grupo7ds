package org.example.Dominio.Incidentes;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Heladeras.EstadoHeladera;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Reportes.GeneradorDeReportes;
import org.example.Dominio.Reportes.GeneradorReporteFallas;
import org.example.Servicio.LocalizadorTecnicos;
import org.example.Dominio.Rol.Tecnico;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // Usa una sola tabla para la herencia
//@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_incidente", discriminatorType = DiscriminatorType.STRING)
public class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne()
    @JoinColumn(name = "heladera_id")
    public Heladera heladera;

    @Column
    @Setter
    private LocalDateTime fechaYHora;

    @OneToMany(mappedBy = "incidente", cascade = CascadeType.ALL)
    @Getter
    private List<Visita> visitas = new ArrayList<Visita>();

    @Transient
    private List<GeneradorDeReportes> suscriptores = new ArrayList<GeneradorDeReportes>();

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

}
