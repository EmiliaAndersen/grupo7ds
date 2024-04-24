public class Giro extends Envio {
    float importe;

    public Giro(Entidad destinatario, Entidad remitente, float importe) {
        super(destinatario, remitente);
        this.importe = importe;
    }
@Override
    public float precio() {
        if (importe > 500) {
            return 1000;
        }
        else {
            return 500;
        }
    }
}