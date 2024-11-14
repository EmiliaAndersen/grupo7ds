package org.example.Dominio.Colaboraciones.Factory;

import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Colaboraciones.OfrecerProductos;
import org.example.Dominio.Colaboraciones.TipoColaborador;
import org.example.Dominio.Reportes.GeneradorDeReportes;
import org.example.Dominio.Reportes.GeneradorReporteViandasColocadas;
import org.example.Dominio.Rol.Colaborador;

import java.time.LocalDate;

public class OfrecerProductosFactory extends ColaboracionFactory {

    public Colaboracion crearColaboracion(String tipo_producto, String marca, int monto) {
//        if(!this.validarTipoColaborador(colaborador, TipoColaborador.P_JURIDICA)){
//            return null;
//        }

        return new OfrecerProductos(tipo_producto, marca, monto);
    }
}
