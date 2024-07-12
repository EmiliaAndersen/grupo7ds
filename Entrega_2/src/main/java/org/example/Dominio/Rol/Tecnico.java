package org.example.Dominio.Rol;

import lombok.Getter;
import org.example.Dominio.AreaDeCobertura.AreaCobertura;

import java.util.List;

public class Tecnico extends Rol{
    @Getter
    private List<AreaCobertura> areaCobertura;
}