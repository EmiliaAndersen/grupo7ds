package org.example.Dominio.Tarjetas;

import lombok.Setter;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Rol.AperturaHeladera;
import org.example.Dominio.Rol.Distribuidor;
import org.example.Servicio.AdministradorPermisos;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "tarjeta_distribuidor")
public class TarjetaDistribuidor extends Tarjeta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "tarjetaDistribuidor")
    private Distribuidor distribuidor;

    @Column
    @Setter
    private LocalDateTime permisoHasta;

    @OneToMany(mappedBy = "tarjetaDistribuidor",cascade = CascadeType.ALL)
    private List<SolicitudPermisos> solicitudesPasadas;



  public boolean validarPermiso(){
      return permisoHasta.isAfter(LocalDateTime.now());
    }

  public SolicitudPermisos solicitarAcceso(Heladera heladera){
      SolicitudPermisos solicitud = new SolicitudPermisos(LocalDateTime.now(), heladera, this);
      solicitudesPasadas.add(solicitud);
      AdministradorPermisos.getInstance().darAcceso(this);
      return solicitud;
  }

  public void abrirHeladera(Heladera heladera) {
    if (validarPermiso()) {
      AperturaHeladera aperturaHeladera = new AperturaHeladera();
      aperturaHeladera.setHeladera(heladera);
      aperturaHeladera.setFechaYHora(LocalDateTime.now());
      distribuidor.registrarApertura(aperturaHeladera);
    } else {
      throw new RuntimeException("Tiempo Limite Excedido");
    }
  }

}
