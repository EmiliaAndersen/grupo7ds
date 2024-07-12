package org.example.Migrador;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.example.Dominio.Colaboraciones.DistribucionDeViandas;
import org.example.Dominio.Colaboraciones.DonacionDeDinero;
import org.example.Dominio.Colaboraciones.DonacionDeVianda;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Documentos.Documento;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import com.sendgrid.*;
import org.example.Dominio.Rol.Colaborador;


public class Migrador {


    public void Migrar(String csvPath,List<PersonaHumana> personas, List<Colaborador> colaboradores){
        List<String[]> filas = leerArchivoCsv(csvPath);
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

            PersonaHumana persona = buscarPersona(personas, tipoDoc, numDocumento);
            if (persona == null) {
                persona = generarPersona(personas, numDocumento, tipoDoc, nombre, apellido);
            }
//            persona.setMail(mail);
            Colaboracion colaboracion;
            colaboracion = generarColaboracion(formaColaboracion, fechaColaboracion, cantidad);
            if (colaboracion == null) return;
            Colaborador colaborador = new Colaborador();
            colaborador.setPersona(persona);
            colaborador.realizarColaboracion(colaboracion);
            enviarMail(mail);
            colaboradores.add(colaborador);
        }
    }

    private static Colaboracion generarColaboracion(String formaColaboracion, LocalDate fechaColaboracion, double cantidad) {
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
            return null;
        }
        return colaboracion;
    }

    private static PersonaHumana generarPersona(List<PersonaHumana> personas, Integer numDocumento, String tipoDoc, String nombre, String apellido) {
        PersonaHumana persona;
        persona = new PersonaHumana();
        Documento documento = new Documento(numDocumento, tipoDoc, "", "");
        persona.setDocumento(documento);
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        personas.add(persona);
        return persona;
    }

    private List<String[]> leerArchivoCsv(String csvPath) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.detectFormatAutomatically();

        CsvParser parser = new CsvParser(settings);

        List<String[]> allRows = null;
        try {
            File file = new File("Entrega_2/src/main/java/org/example/archivo.csv");
            allRows = parser.parseAll(new FileReader(file.getAbsolutePath()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return allRows;
    }

    private PersonaHumana buscarPersona(List<PersonaHumana> personas, String tipoDoc,Integer documento) {
        for (PersonaHumana persona : personas) {
            if (persona.getDocumento().getTipo().equals(tipoDoc) &&
                    persona.getDocumento().getDocumento().equals(documento)) {
                return persona; // La persona ya está registrada
            }
        }
        return null; // La persona no está registrada
    }
    public static void enviarMail(String mailTo) {

        Email from = new Email("grupo7ddstp@gmail.com");
        String subject = "Hello World";
        Email to = new Email(mailTo);
        Content content = new Content("text/plain", "Hello, Email!");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}
