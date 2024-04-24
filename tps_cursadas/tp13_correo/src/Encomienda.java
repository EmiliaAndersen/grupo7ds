public class Encomienda extends Envio {
    String tipoEncomienda;

    public Encomienda(Entidad destinatario, Entidad remitente, float precio, int codRastreo,String tipoEncomienda) {
        super(destinatario, remitente, precio, codRastreo);
    }
}
