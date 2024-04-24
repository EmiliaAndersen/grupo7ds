public class Envio {
    Entidad destinatario;
    Entidad remitente;
    float precio;
    int codRastreo;

    public Envio(Entidad destinatario, Entidad remitente, float precio, int codRastreo) {
        this.destinatario = destinatario;
        this.remitente = remitente;
        this.precio = precio;
        this.codRastreo = codRastreo;
    }

    public Envio enviar(Entidad destinatario, Entidad remitente ){
        return new Envio(destinatario,remitente,4550,0);
    }
}