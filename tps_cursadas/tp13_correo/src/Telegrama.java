public class Telegrama extends Envio{
    String texto;
    String claseTelegrama;

    public Telegrama(Entidad destinatario, Entidad remitente, float precio, int codRastreo,String texto,String claseTelegrama) {
        super(destinatario, remitente, precio, codRastreo);
    }
}
