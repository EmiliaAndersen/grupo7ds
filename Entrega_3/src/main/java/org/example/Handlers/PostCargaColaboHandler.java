package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.Colaborador;
import org.example.Migrador.Migrador;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PostCargaColaboHandler implements Handler {

    public void handle(@NotNull Context ctx) {
        UploadedFile file = ctx.uploadedFile("file");
        String filename = file.filename();
        Path uploadDir = Path.of("uploads");
        Path path = uploadDir.resolve(filename);

        Migrador migrador = new Migrador();
        List<PersonaHumana> listaPersonas = new ArrayList<>();
        List<Colaborador> listaColaboradores = new ArrayList<>();
        migrador.Migrar(path.toString(), listaPersonas, listaColaboradores);

        System.out.println("Archivo procesado");
        ctx.redirect("/perfil");
    }
}