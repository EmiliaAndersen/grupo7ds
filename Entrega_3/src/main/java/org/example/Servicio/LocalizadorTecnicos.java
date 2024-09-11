package org.example.Servicio;

import net.bytebuddy.asm.Advice;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Rol.Tecnico;

public class LocalizadorTecnicos {
    private static LocalizadorTecnicos instance;

    private LocalizadorTecnicos() {}

    public static LocalizadorTecnicos getInstance() {
        if (instance == null) {
            instance = new LocalizadorTecnicos();
        }
        return instance;
    }

    public Tecnico localizarTecnicoCercano(PuntoEstrategico ubicacion) {
        // Recorre los tecnicos que se encuentran persistidos en el sistema, determina el mas cercano y lo devuelve
        return null;
    }

}
