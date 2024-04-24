import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Movimiento {
    Envio envio;
    ArrayList<Sucursal> sucursalesVisitadas;
    Empleado cartero;
    boolean entregado;

    public Movimiento(Envio envio, ArrayList<Sucursal> sucursalesVisitadas, boolean entregado) {
        this.envio = envio;
        this.entregado = entregado;
        this.sucursalesVisitadas = new ArrayList<>();
    }
    public void iniciarEnvio(Envio envio,Empleado cartero, int codRastreo) {
        this.cartero = cartero;
        this.envio.codRastreo = codRastreo;
        this.envio.asignarPrecio();
    }
    public void actualizarEstado() {
        this.entregado = true;
    }
    public void agregarParada(Sucursal nuevaParada){
        this.sucursalesVisitadas.add(nuevaParada);
        this.cartero= asignarCartero(nuevaParada);
    }

    public void asignarCodRastreo(int cod){
       this.envio.codRastreo = cod;
    }

    public Empleado asignarCartero(Sucursal suc){
        return  suc.listaEmpleados.stream().filter(empleado -> empleado.tipoEmpleado.equals("cartero")).toList().get(0);
    }

}
