package org.example.Dominio.Colaboraciones.Factory;

import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Colaboraciones.DonacionDeDinero;
import org.example.Dominio.Colaboraciones.TipoColaborador;
import org.example.Dominio.Rol.Colaborador;

import java.time.LocalDate;

public class DonacionDeDineroFactory extends ColaboracionFactory {
    
    public Colaboracion crearColaboracion(LocalDate fecha, Double monto, String frecuencia){
//        if(!this.validarTipoColaborador(colaborador, TipoColaborador.P_HUMANA) && !this.validarTipoColaborador(colaborador, TipoColaborador.P_JURIDICA) )
//        {
//            return null;
//        }

        LocalDate frecuencia_fecha;
        switch (frecuencia){
            case "Mensual":
                frecuencia_fecha = LocalDate.now().plusMonths(1);
                break;
            case "Semanal":
                frecuencia_fecha = LocalDate.now().plusWeeks(1);
                break;
            case "SinFrecuencia":
                frecuencia_fecha = null;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + frecuencia);
        }

        return new DonacionDeDinero(fecha, monto, frecuencia_fecha);
    }
}
