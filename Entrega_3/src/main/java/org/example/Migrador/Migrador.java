package org.example.Migrador;

import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.Colaborador;

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
        for (String[] row : filas) {
            String tipoDoc = row[0];
            Integer numDocumento = Integer.valueOf(row[1]);
            String nombre = row[2];
            String apellido = row[3];
            String mail = row[4];
            LocalDate fechaColaboracion = LocalDate.parse(row[5]);
            String formaColaboracion = row[6];
            double cantidad = Double.parseDouble(row[7]);

            PersonaHumana persona = personaService.buscarPersona(personas, tipoDoc, numDocumento);
            Colaborador colaborador = new Colaborador();
            if (persona == null) {
                persona = personaService.generarPersona(personas, numDocumento, tipoDoc, nombre, apellido, mail);
                ServiciosColaboracion.generarColaborador(persona, colaborador);
                colaboracionService.asignarColaboracion(colaborador,formaColaboracion, fechaColaboracion, cantidad, mail);
                colaboradores.add(colaborador);
            }else{
                colaborador = colaboracionService.buscarColaboradorPorPersona(colaboradores, persona);
                colaboracionService.asignarColaboracion(colaborador,formaColaboracion, fechaColaboracion, cantidad, mail);
            }
        }
    }
}
