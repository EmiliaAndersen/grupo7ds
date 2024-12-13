package org.example.Dominio.Reportes;

import org.example.Dominio.Incidentes.Incidente;
import org.example.Dominio.Viandas.Vianda;

import java.util.List;

public interface DataService {
    List<Vianda> obtenerViandas();
    List<Incidente> obtenerIncidentes();
}
