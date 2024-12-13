package org.example.Dominio.Reportes;

import org.example.Dominio.Incidentes.Incidente;
import org.example.Dominio.Viandas.Vianda;
import org.example.repositorios.RepositorioIncidente;
import org.example.repositorios.RepositorioVianda;

import java.util.List;

public class DataServiceRep implements DataService{

    private final RepositorioIncidente repositorioIncidente;
    private final RepositorioVianda repositorioVianda;

    public DataServiceRep(RepositorioIncidente repositorioIncidente, RepositorioVianda repositorioVianda) {
        this.repositorioIncidente = repositorioIncidente;
        this.repositorioVianda = repositorioVianda;
    }

    @Override
    public List<Incidente> obtenerIncidentes(){
        return repositorioIncidente.obtenerIncidentes();
    }

    @Override
    public List<Vianda> obtenerViandas(){
        return repositorioVianda.obtenerViandas();
    }
}
