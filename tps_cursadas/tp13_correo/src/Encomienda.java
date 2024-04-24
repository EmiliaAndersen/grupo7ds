public class Encomienda extends Envio {
    String tipoEncomienda;

    public Encomienda(Entidad destinatario, Entidad remitente, String tipoEncomienda) {
        super(destinatario, remitente);
        this.tipoEncomienda = tipoEncomienda;
    }
@Override
    public float precio(){
        if(tipoEncomienda=="De persona"){
            return 400;
        }
        else{
            return 200;
        }
    }
}
