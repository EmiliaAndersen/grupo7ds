public class Envio {
    Entidad destinatario;
    Entidad remitente;
    float precio;
    int codRastreo;

    public Envio(Entidad destinatario, Entidad remitente) {
        this.destinatario = destinatario;
        this.remitente = remitente;
    }

    public float precio(){
        return 0;
    }

    public void asignarPrecio(){
        this.precio = this.precio();
    }

}