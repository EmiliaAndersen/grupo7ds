public class Telegrama extends Envio{
    String texto;
    String claseTelegrama;

    public Telegrama(Entidad destinatario, Entidad remitente,String texto,String claseTelegrama) {
        super(destinatario, remitente);
        this.texto = texto;
        this.claseTelegrama = claseTelegrama;
    }

    @Override
    public float precio(){
    return 200;
    }
}
