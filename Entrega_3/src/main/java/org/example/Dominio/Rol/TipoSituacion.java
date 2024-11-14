package org.example.Dominio.Rol;

import lombok.Getter;
import lombok.Setter;

import org.example.Dominio.Documentos.Documento;
import org.example.Dominio.Tarjetas.TarjetaVulnerable;

import javax.persistence.*;
import java.time.LocalDate;


public enum TipoSituacion{
    SINVIVIENDA,
    DOMICILIO;
  }