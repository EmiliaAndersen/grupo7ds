package org.example.Dominio.Colaboraciones;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.example.BDUtils;
import org.example.Dominio.Heladeras.ActividadHeladera;
import org.example.Dominio.Heladeras.EstadoHeladera;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Rol.Colaborador;
import org.example.Servicio.LocalizacionEstrategicaAPI;

import javax.persistence.*;

@Entity
@Table(name = "hacerse_cargo_heladera")
public class HacerseCargoDeHeladera extends Colaboracion {
  @Column
  @Setter
  private LocalDate fechaInicio;


  @OneToOne
  @JoinColumn(name = "punto_estrategico_id",referencedColumnName = "id")
  @Getter
  private PuntoEstrategico puntoEstrategico;
  @Column
  @Getter
  private Double radio;

  @OneToOne
  @JoinColumn(name = "heladera_id",referencedColumnName = "id")
  @Getter
  @Setter
  private Heladera heladera;

  @Transient
  private final LocalizacionEstrategicaAPI localizacionEstrategicaAPI;

  public HacerseCargoDeHeladera(LocalizacionEstrategicaAPI localizacionEstrategicaAPI, PuntoEstrategico puntoEstrategico, Double radio) {
    this.localizacionEstrategicaAPI = localizacionEstrategicaAPI;
    this.puntoEstrategico = puntoEstrategico;
    this.radio = radio;
  }
  public HacerseCargoDeHeladera(PuntoEstrategico puntoEstrategico, Heladera heladera,LocalDate fechaInc){
    this.puntoEstrategico = puntoEstrategico;
    this.heladera = heladera;
    this.localizacionEstrategicaAPI = null;
    this.fechaInicio = fechaInc;
  }


  //TODO: revisar esto porque si saco el null tira error por todos lados
  public HacerseCargoDeHeladera() {
    this.localizacionEstrategicaAPI = null;
  }


  @Override
  public void procesarColaboracion(Colaborador colaborador) {
//
//    if (isTypeOf(colaborador, TipoColaborador.P_JURIDICA)) {
//      //Ver
//    }
  }

  @Override
  public Double calcularPuntos() {
    LocalDate fechaActual = LocalDate.now();
    Period periodo = Period.between(fechaInicio,fechaActual);

    EntityManager em = BDUtils.getEntityManager();

    Long colaboradorId = colaborador.getId();

    List<HacerseCargoDeHeladera> colaboraciones = em.createQuery(
      "SELECT h FROM HacerseCargoDeHeladera h " +
      "WHERE  h.colaborador.id = :colaborador AND h.heladera.estado = :estadoActiva", 
      HacerseCargoDeHeladera.class)
      .setParameter("colaborador",colaboradorId)
      .setParameter("estadoActiva", EstadoHeladera.ACTIVA)
      .getResultList();

    int cantidadHeladerasActivas = colaboraciones.size();

    int totalMesesActivos = 1;

    for (HacerseCargoDeHeladera colaboracion : colaboraciones) {
        Heladera heladera = colaboracion.getHeladera();
        if (heladera != null) {
            List<ActividadHeladera> mesesActiva = heladera.mesesActiva;
            // Si la lista no es nula, sumar su tamaño
            if (mesesActiva != null) {
                totalMesesActivos += mesesActiva.size();
            }
        }
    }


    return (double) cantidadHeladerasActivas * totalMesesActivos * 5;
}


  public List<PuntoEstrategico> getLocalizacionEstrategica(){

    return localizacionEstrategicaAPI.getPuntoEstrategico(puntoEstrategico, radio);
  }


}
