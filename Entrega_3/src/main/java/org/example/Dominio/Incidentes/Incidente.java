package org.example.Dominio.Incidentes;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Heladeras.EstadoHeladera;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Reportes.GeneradorDeReportes;
import org.example.Servicio.LocalizadorTecnicos;
import org.example.Dominio.Rol.Tecnico;
import org.example.repositorios.RepositorioTecnicos;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="incidente")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // Usa una sola tabla para la herencia
//@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_incidente", discriminatorType = DiscriminatorType.STRING)
public class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "heladera_id")
    public Heladera heladera;

    @Column
    @Setter
    @Getter
    private LocalDateTime fechaYHora;

    @OneToMany(mappedBy = "incidente", cascade = CascadeType.ALL)
    @Getter
    private List<Visita> visitas = new ArrayList<Visita>();

    @Getter
    @Setter
    @Column
    private Boolean estaActiva;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

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

    public void asignarTecnico(Heladera heladera){
        RepositorioTecnicos repositorioTecnicos = RepositorioTecnicos.getInstance();

        List<Tecnico> tecnicos = repositorioTecnicos.obtenerTecnicos();

        Tecnico tecnicoMasCercano = null;
        double distanciaMinima = Double.MAX_VALUE;

        for (Tecnico tecnico : tecnicos) {


            double distancia = calcularDistanciaHaversine(
                heladera.getUbicacion().getLatitud(),
                heladera.getUbicacion().getLongitud(),
                tecnico.getAreaCobertura().getLatitudCentro(),
                tecnico.getAreaCobertura().getLongitudCentro()
            );

            // Actualiza el técnico más cercano si la distancia es menor
            if (distancia < distanciaMinima && distancia < tecnico.getAreaCobertura().getRadio()) {
                distanciaMinima = distancia;
                tecnicoMasCercano = tecnico;
            }
        } //TODO: Charlar que hacer si no encuentra un tecnico en el radio de cobertura
        if (tecnicoMasCercano != null){
            this.setTecnico(tecnicoMasCercano);
        }
    }

    public static double calcularDistanciaHaversine(double lat1, double lon1, double lat2, double lon2) {
        final int RADIO_TIERRA = 6371; // Radio de la Tierra en km WTF GRACIAS CHATGPT!!

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADIO_TIERRA * c;
    }
}


