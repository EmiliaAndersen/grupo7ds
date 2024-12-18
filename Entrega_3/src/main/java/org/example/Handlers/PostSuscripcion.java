package org.example.Handlers;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.example.BDUtils;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.MediosContacto.MedioDeContacto;
import org.example.Dominio.MediosContacto.TipoMedioContacto;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Suscripciones.Suscriptor;
import org.example.Dominio.Suscripciones.TipoSuscripcion;
import org.example.repositorios.RepositorioColaboradores;
import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PostSuscripcion implements @NotNull Handler {
    public void handle(@NotNull Context context){
        String num = context.formParam("motivo");
        if ("1".equals(num) || "2".equals(num) || "3".equals(num)) {
            
            System.out.println("Motivo valido");
            EntityManager em = BDUtils.getEntityManager();
            BDUtils.comenzarTransaccion(em);
            try{
                Suscriptor sr = new Suscriptor();

                RepositorioColaboradores repositorioColaboradores = RepositorioColaboradores.getInstance();
                String nombre = context.sessionAttribute("username");
                Colaborador colab = repositorioColaboradores.obtenerColaborador(nombre);
                TipoMedioContacto tipoMedioContacto = null;
                if (context.formParam("tipo_medio_contacto").equals("CORREO_ELECTRONICO")){
                     tipoMedioContacto = TipoMedioContacto.CORREO_ELECTRONICO;
                } else if (context.formParam("tipo_medio_contacto").equals("WHATSAPP")) {
                     tipoMedioContacto = TipoMedioContacto.WHATSAPP;
                }

                MedioDeContacto medioDeContacto = new MedioDeContacto();
                medioDeContacto.setPersona(colab.getPersona());
                medioDeContacto.setTipo(tipoMedioContacto);
                medioDeContacto.setDetalle(context.formParam("contacto"));
                em.persist(medioDeContacto);

                sr.setColaborador(colab); 
                sr.setMdc(medioDeContacto);
                String heladeraId = context.formParam("heladera");
                Long helidlong;
                helidlong = Long.parseLong(heladeraId);
                


                TypedQuery<Heladera> query = em.createQuery("SELECT h from Heladera h where h.id = :id ",Heladera.class);
                query.setParameter("id",helidlong);
                Heladera heladera;
                heladera = query.getSingleResult();

                
                sr.setHeladera(heladera);

               
           
                if ("1".equals(num) ) {
                    sr.setTipo(TipoSuscripcion.RESTANTES);
                    sr.setNumeroAviso(stringToInt(context.formParam("viandas")));
                }else if ("2".equals(num) ) {
                    sr.setTipo(TipoSuscripcion.FALTANTES);
                    sr.setNumeroAviso(stringToInt(context.formParam("viandas")));
                }else if("3".equals(num)){
                    sr.setTipo(TipoSuscripcion.DESPERFECTO);
                  //  sr.setNumeroAviso(stringToInt(context.formParam("viandas")));

                }

                


                em.persist(sr);
                BDUtils.commit(em);
                context.sessionAttribute("successSuscripcion", true);
                context.redirect("/suscripcion");
            } catch (Exception e) {
                BDUtils.rollback(em);
            }
        }else{
            
            System.out.println("Motivo no valido: Ponga 1,2 o 3");
            System.out.println(num);
            num=num;
            context.sessionAttribute("ErrorMotivo", true);
            context.redirect("/suscripcion");
        }
    }
    public static int stringToInt(String str) {
            return Integer.parseInt(str);
    }

}
