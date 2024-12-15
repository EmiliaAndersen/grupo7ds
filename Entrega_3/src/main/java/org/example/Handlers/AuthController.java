package org.example.Handlers;

import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.api.client.json.webtoken.JsonWebToken.Payload;
import com.google.auth.oauth2.IdToken;
import com.google.auth.oauth2.TokenVerifier;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.example.BDUtils;
import org.example.Dominio.Documentos.Documento;
import org.example.Dominio.MediosContacto.MedioDeContacto;
import org.example.Dominio.MediosContacto.TipoMedioContacto;
import org.example.Dominio.Persona.Persona;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Rol.Rol;
import org.example.Validador.Usuario;
import org.example.repositorios.RepositorioColaboradores;
import org.example.repositorios.RepositorioUsuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class AuthController implements Handler {

    @Override
    public void handle(Context ctx){
        // Extraer el cuerpo de la solicitud (GoogleAuthRequest)
        String credential = ctx.formParam("credential");
        if (credential == null || credential.isEmpty()) {
            ctx.status(400).result("Token faltante");
            return;
        }
        Map<String, Object> model = new HashMap<>();
        RepositorioUsuarios repoUsuarios = RepositorioUsuarios.getRepositorioUsuarios();
        RepositorioColaboradores repoColab = RepositorioColaboradores.getInstance();

        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);

        try {
     
       
            TokenVerifier verifier = TokenVerifier.newBuilder()
                    .setAudience("309416222679-catqljsod4pkj8u2vvnub30jmm33s2ga.apps.googleusercontent.com")
                    .build();
        
            JsonWebSignature jws = verifier.verify(credential);  // Esto devuelve un JsonWebSignature, no un IdToken
            JsonWebToken.Payload payload = jws.getPayload();
        
            // Extraer los valores del payload (como email y nombre)
            String email = payload.get("email").toString();
            String nombre = payload.get("given_name").toString();
            String apellido = payload.get("family_name").toString();

            if (repoUsuarios.verificarUsuarios(email)) {
                // Crear un nuevo usuario
                Usuario usu = new Usuario(email, nombre);
                repoUsuarios.addUsuario(usu);

                PersonaHumana p = new PersonaHumana();

                String dateString = "12-12-2003";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate loc = LocalDate.parse(dateString, formatter);

                //p.setFechaDeNacimiento(loc);
                p.setNombre(nombre);
                p.setApellido(apellido);
                p.setDireccion("");
                p.setUsuario(usu);
                MedioDeContacto m = new MedioDeContacto();
                m.setPersona(p);
                m.setTipo(TipoMedioContacto.CORREO_ELECTRONICO);
                m.setDetalle(email);
             
                p.getMediosDeContacto().add(m);

                
                em.persist(p);

                BDUtils.commit(em);

                instanciarColaborador(ctx, p);
                
    
            
                ctx.sessionAttribute("successModify",null);
                ctx.sessionAttribute("username",email);
                ctx.sessionAttribute("rol","noAdmin");
               
                ctx.sessionAttribute("tipo_persona", "persona_humana");
                ctx.render("/templates/perfil_persona_humana.mustache", model);

                ctx.sessionAttribute("succesLogin", true);
                ctx.redirect("/front_page");
               
    
            } else {
                
                ctx.sessionAttribute("successModify",null);
                ctx.sessionAttribute("username",email);
                ctx.sessionAttribute("rol","noAdmin");
               
                ctx.sessionAttribute("tipo_persona", "persona_humana");
                ctx.render("/templates/perfil_persona_humana.mustache", model);

                ctx.sessionAttribute("succesLogin", true);
                ctx.redirect("/front_page");
              
              
            }
        } catch (Exception e) {
            e.printStackTrace();
            BDUtils.rollback(em);
            ctx.result("Error al autenticar el usuario: " + e.getMessage());
        }
    }        



    
  public void instanciarColaborador(Context context, Persona persona){
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    Colaborador col = new Colaborador();
    col.setPersona(persona);
    em.persist(col);
    BDUtils.commit(em);
  }

}