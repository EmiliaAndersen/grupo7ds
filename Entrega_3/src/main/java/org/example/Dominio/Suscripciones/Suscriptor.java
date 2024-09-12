package org.example.Dominio.Suscripciones;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.MediosContacto.MedioDeContacto;
import org.example.Dominio.MediosContacto.TipoMedioContacto;
import org.example.Dominio.Rol.Colaborador;


import java.util.List;

import static org.example.Dominio.Notificador.ServiciosMail.enviarMail;

@Getter
@Setter
public class Suscriptor {
    private Colaborador colaborador;
    private TipoSuscripcion tipo;
    private List<Notificacion> mensajesEnviados;
    private int numeroAviso = 0;

    private String armarNotificacion(){
        String mensaje = "";
        if(tipo == TipoSuscripcion.RESTANTES){
            mensaje = "RESTAN X";
        }
        else if(tipo == TipoSuscripcion.FALTANTES){
            mensaje = "FALTAN X";
        }
        else if(tipo == TipoSuscripcion.DESPERFECTO){
            mensaje = "RECOMENDAMOS ESTAS HELADERAS: ";
        }
        Notificacion noti = new Notificacion();
        noti.setMensajeEnviado(mensaje);
        mensajesEnviados.add(noti);
        return mensaje;

    }
    public void notificarSuscriptor(){
        String mail;
        armarNotificacion();
        for (MedioDeContacto medio : colaborador.getPersona().getMediosDeContacto()){
            if(medio.getTipo() == TipoMedioContacto.CORREO_ELECTRONICO){
                mail = medio.getDetalle();
                enviarMail(mail, armarNotificacion());

            }
        }
    }

}
