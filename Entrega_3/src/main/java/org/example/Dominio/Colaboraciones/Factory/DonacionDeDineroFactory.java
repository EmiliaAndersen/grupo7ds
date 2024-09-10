package org.example.Dominio.Colaboraciones.Factory;

import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Colaboraciones.DonacionDeDinero;
import org.example.Dominio.Colaboraciones.TipoColaborador;
import org.example.Dominio.Rol.Colaborador;

import java.time.LocalDate;

public class DonacionDeDineroFactory extends ColaboracionFactory {
    
    public Colaboracion crearColaboracion(Colaborador colaborador, LocalDate fecha, Double monto, LocalDate frecuencia){
        if(!this.validarTipoColaborador(colaborador, TipoColaborador.P_HUMANA) && !this.validarTipoColaborador(colaborador, TipoColaborador.P_JURIDICA) )
        {
            return null;
        }

        return new DonacionDeDinero(fecha, monto, frecuencia);
    }
}
