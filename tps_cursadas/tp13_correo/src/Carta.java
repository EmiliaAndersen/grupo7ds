public class Carta extends Envio {
    String tipoCarta;
    String sellado;

    public Carta(Entidad destinatario, Entidad remitente, String tipoCarta, String sellado) {
        super(destinatario, remitente);
        this.tipoCarta = tipoCarta;
        this.sellado = sellado;
    }
@Override
    public float precio() {
        if (tipoCarta.equals("Simple")) {
            return 100;
        } else if (tipoCarta.equals("Certificadas")) {
            return 200;
        } else
            return 300;

    }
}

