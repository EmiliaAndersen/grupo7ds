package org.example.repositorios;

import org.example.Dominio.Heladeras.Heladera;

public class RepositorioHeladeras {

    private static RepositorioHeladeras instance;

    public RepositorioHeladeras() {}

    public static RepositorioHeladeras getInstance(){
        if (instance == null) {
            instance = new RepositorioHeladeras();
        }
        return instance;
    }

    public Heladera obtenerHeladera(String heladeraId){
        return null;
    }
}
