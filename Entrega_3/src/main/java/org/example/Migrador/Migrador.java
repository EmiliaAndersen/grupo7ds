package org.example.Migrador;

import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.Colaborador;
import org.example.repositorios.RepositorioColaboradores;

import java.time.LocalDate;
import java.util.List;

public class Migrador {

    private final LectorCsv lector = new LectorCsv();
    private final ServiciosPersona personaService = new ServiciosPersona();
    private final ServiciosColaboracion colaboracionService = new ServiciosColaboracion();

    public void Migrar(String csvPath, List<PersonaHumana> personas, List<Colaborador> colaboradores) {
        List<String[]> filas = lector.leerArchivoCsv(csvPath);
        procesarFilas(personas, filas, colaboradores);
    }

    private void procesarFilas(List<PersonaHumana> personas, List<String[]> filas, List<Colaborador> colaboradores) {
        RepositorioColaboradores repositorioColaboradores = RepositorioColaboradores.getInstance();
        for (String[] row : filas) {
            String tipoDoc = row[0];
            Integer numDocumento = Integer.valueOf(row[1]);
            String nombre = row[2];
            String apellido = row[3];
            String mail = row[4];
            LocalDate fechaColaboracion = LocalDate.parse(row[5]);
            String formaColaboracion = row[6];
            double cantidad = Double.parseDouble(row[7]);

            PersonaHumana persona = personaService.buscarPersona(personas, nombre,apellido, numDocumento);
            Colaborador colaborador;

            // Si la persona no existe, la creamos
            if(persona == null){
                persona = personaService.generarPersona(personas, numDocumento, tipoDoc, nombre, apellido, mail);
                colaborador = new Colaborador();
                ServiciosColaboracion.generarColaborador(persona, colaborador);
            }
            else{
                colaborador = repositorioColaboradores.getColaboradorPersona(persona);
            }

            colaboracionService.asignarColaboracion(colaborador, formaColaboracion, fechaColaboracion, cantidad, mail);
        }
    }
}
