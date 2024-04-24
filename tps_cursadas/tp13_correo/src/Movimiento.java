import java.util.ArrayList;

public class Movimiento {
    Envio envio;
    ArrayList<Sucursal> sucursalesVisitadas;
    Empleado cartero;
    boolean entregado;

    public Movimiento(Envio envio, ArrayList<Sucursal> sucursalesVisitadas, Empleado cartero, boolean entregado) {
        this.envio = envio;
        this.cartero = cartero;
        this.entregado = entregado;
        this.sucursalesVisitadas = new ArrayList<>();
    }
    public void iniciarEnvio(Envio envio, Entidad destinatario, Entidad remitente,Empleado cartero,int codRastreo) {
        envio.enviar(destinatario, remitente);
        this.asignarCartero(cartero);
        this.asignarCodRastreo(codRastreo);
    }

    public void agregarParada(Sucursal nuevaParada){
        this.sucursalesVisitadas.add(nuevaParada);
    }
    public void actualizarEstado() {
        this.entregado = true;
    }
    public void asignarCartero(Empleado cartero){
        this.cartero = cartero;
    }
    public void asignarCodRastreo(int cod){
        this.envio.codRastreo=cod;
    }
}
