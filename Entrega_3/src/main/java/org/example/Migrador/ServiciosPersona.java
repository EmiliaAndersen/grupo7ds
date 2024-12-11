package org.example.Migrador;

import org.example.BDUtils;
import org.example.Dominio.Documentos.Documento;
import org.example.Dominio.MediosContacto.MedioDeContacto;
import org.example.Dominio.Persona.PersonaHumana;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.example.Dominio.MediosContacto.TipoMedioContacto.CORREO_ELECTRONICO;

public class ServiciosPersona {
    public PersonaHumana buscarPersona(List<PersonaHumana> personas, String nombre, String apellido , Integer documento) {
        for (PersonaHumana persona : personas) {
            if (persona.getNombre() != null && persona.getNombre().equals(nombre) && persona.getApellido() != null && persona.getApellido().equals(apellido) &&
                    persona.getDocumento() != null  && persona.getDocumento().getDocumento().equals(documento)) {
                return persona;
            }
        }
        return null;
    }

    public PersonaHumana generarPersona(List<PersonaHumana> personas, Integer numDocumento, String tipoDoc, String nombre, String apellido, String mail) {
        PersonaHumana persona = new PersonaHumana();
        Documento documento = new Documento(numDocumento, tipoDoc, "", "");
        persona.setDocumento(documento);
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        personas.add(persona);

        MedioDeContacto contacto = new MedioDeContacto();
        contacto.setTipo(CORREO_ELECTRONICO);
        contacto.setDetalle(mail);
        contacto.setPersona(persona);

        List<MedioDeContacto> medios = new ArrayList<>();
        medios.add(contacto);
        persona.setMediosDeContacto(medios);

        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        em.persist(documento);
        em.persist(persona);
        em.persist(contacto);
        BDUtils.commit(em);

        return persona;
    }
}
