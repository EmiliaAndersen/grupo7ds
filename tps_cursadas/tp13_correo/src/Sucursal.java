
import java.util.ArrayList;

public class Sucursal {
    int numero;
    String domicilio;
    String localidad;
    ArrayList<Empleado> listaEmpleados;

    public Sucursal(int numero, String domicilio, String localidad, ArrayList<Empleado> listaEmpleados) {
        this.numero = numero;
        this.domicilio = domicilio;
        this.localidad = localidad;
        this.listaEmpleados = listaEmpleados;
    }
}
