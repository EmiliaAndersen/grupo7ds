public class Empleado {
    String tipoEmpleado;
    String nombre;
    int dni;

    public Empleado(String tipoEmpleado, String nombre, int dni) {
        this.dni = dni;
        this.tipoEmpleado = tipoEmpleado;
        this.nombre = nombre;
    }
}
