public class Entidad {
    String tipoEntidad;
    String nombre;
    String localidad;
    int codPostal;
    String direccion;

    public Entidad(String tipoEntidad, String nombre, String localidad, int codPostal,String direccion) {
        this.tipoEntidad = tipoEntidad;
        this.nombre = nombre;
        this.localidad = localidad;
        this.codPostal = codPostal;
        this.direccion = direccion;
    }

    public void mostrarDatos(){
        System.out.println("Tipo Entidad: " + tipoEntidad);
        System.out.println("Nombre: " + nombre);
        System.out.println("Localidad: " + localidad);
        System.out.println("CodPostal: " + codPostal);
        System.out.println("Direccion: " + direccion);
    }
}
