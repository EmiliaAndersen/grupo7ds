package org.example.Dominio.Colaboraciones.Factory;


import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Colaboraciones.RegistrarPersonasEnSituacionVulnerable;
import org.example.Dominio.Colaboraciones.TipoColaborador;
import org.example.Dominio.Rol.Colaborador;

public class RegistrarPersonasFactory extends ColaboracionFactory {
    public Colaboracion crearColaboracion() {
//        if(!this.validarTipoColaborador(colaborador, TipoColaborador.P_HUMANA)){
//            return null;
//        }

        return new RegistrarPersonasEnSituacionVulnerable();
    }
}

