package org.example.Dominio.Suscripciones;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Incidentes.Visita;
import org.example.Dominio.MediosContacto.MedioDeContacto;
import org.example.Dominio.MediosContacto.TipoMedioContacto;
import org.example.Dominio.Rol.Colaborador;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.Dominio.Notificador.ServiciosMail.enviarMail;

@Getter
@Setter
@Entity
@Table(name="suscriptor")
public class Suscriptor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id",  nullable = true)
    private Colaborador colaborador;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "medio_de_contacto_id",referencedColumnName = "id", nullable = true)
    private MedioDeContacto mdc;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private TipoSuscripcion tipo;

    @OneToMany(mappedBy = "suscriptor", cascade = CascadeType.ALL)
    @Getter
    private List<Notificacion> mensajesEnviados = new ArrayList<Notificacion>();

    @ManyToOne
    @JoinColumn(name = "heladera_id")
    public Heladera heladera;

    @Column
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
