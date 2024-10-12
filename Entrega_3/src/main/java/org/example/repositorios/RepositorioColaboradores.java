package org.example.repositorios;

import org.example.Dominio.Rol.Colaborador;

public class RepositorioColaboradores {

    private static RepositorioColaboradores instance;

    public RepositorioColaboradores() {}

    public static RepositorioColaboradores getInstance(){
        if (instance == null) {
            instance = new RepositorioColaboradores();
        }
        return instance;
    }

    public Colaborador obtenerColaborador(String idColaborador){
        return null;
    }
}
