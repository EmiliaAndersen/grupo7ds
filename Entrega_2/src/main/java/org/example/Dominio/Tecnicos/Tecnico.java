package org.example.Dominio.Tecnicos;

import lombok.Getter;
import org.example.Dominio.Documentos.Documento;
import org.example.Dominio.MediosContacto.MedioDeContacto;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;

import java.util.List;

public class Tecnico {
  @Getter
  private String nombre;
  @Getter
  private String apellido;
  @Getter
  private Documento documento;
  @Getter
  private String cuil;
  @Getter
  private List<MedioDeContacto> medioDeContacto;
  @Getter
  private List<AreaCobertura> areaCobertura;
}
