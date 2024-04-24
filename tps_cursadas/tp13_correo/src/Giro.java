public class Giro extends Envio{
    float importe;

    public Giro(Entidad destinatario, Entidad remitente, float precio, int codRastreo,float importe) {
        super(destinatario, remitente, precio, codRastreo);
    }
}
