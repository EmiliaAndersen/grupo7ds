package org.example.Dominio.Heladeras;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.digester.annotations.rules.SetTop;
import org.example.Dominio.Heladeras.sensores.Sensor;
import org.example.Dominio.Incidentes.Alerta;
import org.example.Dominio.Incidentes.Incidente;
import org.example.Dominio.Incidentes.TipoAlerta;
import org.example.Dominio.Incidentes.Visita;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Suscripciones.Suscriptor;
import org.example.Dominio.Suscripciones.TipoSuscripcion;
import org.example.Dominio.Reportes.GeneradorDeReportes;
import org.example.Dominio.Reportes.GeneradorReporteViandasRetiradas;

import org.example.Dominio.Viandas.Vianda;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="heladera")
public class Heladera {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;


    @OneToOne
    @JoinColumn(name = "punto_estrategico_id", referencedColumnName = "id", unique = true, nullable = false)
    @Getter
    private PuntoEstrategico ubicacion;



    @Column
    private LocalDate fechaInicioFuncionamiento;

    @Column
    @Setter
    @Getter
    private float temperaturaMaxima;

    @Column
    @Setter
    @Getter
    private float temperaturaMinima;

    @Column
    @Setter
    @Getter
    private Integer capacidad;

    @Column(nullable = false)
    @Setter
    @Getter
    private Integer stock = 0; 

    @Enumerated(EnumType.STRING)
    @Setter
    @Getter
    public EstadoHeladera estado;

    @OneToMany(mappedBy = "heladera", cascade = CascadeType.ALL)
    private List<Vianda> viandas;

    @OneToMany(mappedBy = "heladera", cascade = CascadeType.ALL)
    public List <Suscriptor>suscriptores;

    @OneToMany(mappedBy = "heladera", cascade = CascadeType.ALL)
    @Setter
    public List<ActividadHeladera> mesesActiva;

    @OneToMany(mappedBy = "heladera", cascade = CascadeType.ALL)
    public List<Sensor> sensores;

    @OneToMany(mappedBy = "heladera", cascade = CascadeType.ALL)
    public List<Incidente> incidentes;



    public Heladera(float temperaturaMaxima, float temperaturaMinima, PuntoEstrategico ubicacion, Integer capacidad){
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
        this.ubicacion = ubicacion;
        this.viandas = new ArrayList<Vianda>();
        this.fechaInicioFuncionamiento = LocalDate.now();
        this.estado = EstadoHeladera.ACTIVA;
        this.mesesActiva = new ArrayList<ActividadHeladera>();
        this.suscriptores = new ArrayList<Suscriptor>();
        this.capacidad = capacidad;
        this.stock= 0;
    }
    public Heladera() {
        // Constructor vacío para JPA
    }


    public boolean validarTemperaturaFuncional(float unaTemperatura){
        if(unaTemperatura > temperaturaMaxima){
            estado = EstadoHeladera.TEMPERATURA_ALTA;
            this.agregarAlerta(TipoAlerta.TEMPERATURA);
            return false;
        }
        if(unaTemperatura < temperaturaMinima){
            estado = EstadoHeladera.TEMPERATURA_BAJA;
            this.agregarAlerta(TipoAlerta.TEMPERATURA);
            return false;
        }
        return true;
    }
    
    public void agregarVianda(Vianda unaVianda){
        this.viandas.add(unaVianda);
        for (Suscriptor suscriptor : suscriptores) {
            if(suscriptor.getTipo() == (TipoSuscripcion.FALTANTES)){
                if(suscriptor.getNumeroAviso() >= viandas.size()){
                    suscriptor.notificarSuscriptor();
                }
            }
        }

    }

    public boolean retirarVianda(){
        if(this.viandas.isEmpty()){
            return false;
        }
        Vianda unaVianda = this.viandas.remove(0);
        unaVianda.retirar();

        for (Suscriptor suscriptor : suscriptores) {
            if(suscriptor.getTipo() == (TipoSuscripcion.RESTANTES)){
                if(suscriptor.getNumeroAviso() <= viandas.size()){
                    suscriptor.notificarSuscriptor();
                }
            }
        }
      
        GeneradorReporteViandasRetiradas reportes = GeneradorReporteViandasRetiradas.getInstance();
        reportes.update();
        return true;
    }

    public void agregarAlerta(TipoAlerta tipoAlerta){
        Alerta alerta = new Alerta(tipoAlerta,this, LocalDateTime.now());
        this.setEstado(EstadoHeladera.INACTIVA);
    }

    public void agregarSuscriptor(Suscriptor suscriptor){
        suscriptores.add(suscriptor);
    }
}
