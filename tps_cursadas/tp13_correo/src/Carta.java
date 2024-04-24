public class Carta extends Envio {
    String tipoCarta;
    String sellado;

    public Carta(Entidad destinatario, Entidad remitente, float precio, int codRastreo,String tipoCarta,String sellado) {
        super(destinatario, remitente, precio, codRastreo);
        this.tipoCarta = tipoCarta;
        this.sellado = sellado;
    }

}
