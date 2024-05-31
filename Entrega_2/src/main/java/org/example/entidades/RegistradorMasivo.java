package org.example.entidades;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.example.entidades.Colaboraciones.DistribucionDeViandas;
import org.example.entidades.Colaboraciones.DonacionDeDinero;
import org.example.entidades.Colaboraciones.DonacionDeVianda;
import org.example.entidades.PersonaHumana;
import org.example.entidades.Colaboraciones.Colaboracion;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class RegistradorMasivo {


    public void migrar(String csvPath){
        List<String[]> filas = leerArchivoCsv(csvPath);

        List<PersonaHumana> personas = new ArrayList<>();

        for (String[] row : filas) {
            String tipoDoc = row[0];
            Integer documento = Integer.valueOf(row[1]);
            String nombre = row[2];
            String apellido = row[3];
            String mail = row[4];
            LocalDate fechaColaboracion = LocalDate.parse(row[5]);
            String formaColaboracion = row[6];
            double cantidad = Double.parseDouble(row[7]);


            PersonaHumana persona = buscarPersona(personas, tipoDoc, documento);
            if (persona == null) {
                persona = new PersonaHumana();
                persona.setTipoDoc(tipoDoc);
                persona.setDocumento(documento);
                persona.setNombre(nombre);
                persona.setApellido(apellido);
                personas.add(persona);
            }
//            persona.setMail(mail);

            Colaboracion colaboracion;
            if(Objects.equals(formaColaboracion, "DINERO")){
                colaboracion = new DonacionDeDinero(fechaColaboracion, cantidad, null);
            }else if(Objects.equals(formaColaboracion, "DONACION_VIANDAS")){
                colaboracion = new DonacionDeVianda();
            }else if(Objects.equals(formaColaboracion, "REDISTRIBUCION_VIANDAS")){
                colaboracion = new DistribucionDeViandas();
            }
//            else if(Objects.equals(formaColaboracion, "ENTREJA_TARJETA")){
//                AGREGAR ENTREGA TARJETA
//            }
            else{
                return;
            }

            persona.agregarColaboracion(colaboracion);



            //enviar_mail(mail);
        }

        //chequeo q haya salido todo bien
        for (PersonaHumana persona : personas){
            System.out.println(persona.getNombre());
            System.out.println(persona.getColaboraciones());
        }
    }

    private List<String[]> leerArchivoCsv(String csvPath) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.detectFormatAutomatically();

        CsvParser parser = new CsvParser(settings);

        List<String[]> allRows = null;
        try {
            allRows = parser.parseAll(new FileReader(csvPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return allRows;
    }

    private PersonaHumana buscarPersona(List<PersonaHumana> personas, String tipoDoc,Integer documento) {
        for (PersonaHumana persona : personas) {
            if (persona.getTipoDoc().equals(tipoDoc) &&
                    persona.getDocumento().equals(documento)) {
                return persona; // La persona ya está registrada
            }
        }
        return null; // La persona no está registrada
    }
}