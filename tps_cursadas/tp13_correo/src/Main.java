import java.util.ArrayList;

public class Main {
    public static void main(String[] args){

    // EMPLEADOS

    Empleado emp1= new Empleado("cartero","emi",45378233);
    Empleado emp2= new Empleado("cartero","cami",45453233);
    Empleado emp3= new Empleado("administrativo","valen",434553233);


    //LISTA EMPLEADOS

    ArrayList<Empleado> listaEmpleados1 = new ArrayList();
    ArrayList<Empleado> listaEmpleados2 = new ArrayList();

    listaEmpleados1.add(emp1);
    listaEmpleados2.add(emp2);
    listaEmpleados2.add(emp3);

    //SUCURSALES

    Sucursal suc1 = new Sucursal(1, "Juan b Justo 101", "CABA", listaEmpleados1);
    Sucursal suc2 = new Sucursal(2, "Dorrego 2123", "CABA", listaEmpleados2);

    //ENTIDADES
    Entidad persona1= new Entidad("persona","pablo","CABA",343,"Santa Fe 33");
    Entidad empresa1= new Entidad("empresa","Arcor","CABA",322,"Av.Libertador 45");
    Entidad persona2= new Entidad("persona","juan","CABA",556," Pueyrredon 533");
    Entidad persona3= new Entidad("persona","lucas","CABA",335,"Av. Rivadavia 342");
    Entidad empresa2= new Entidad("empresa","Mc. donals","CABA",664,"Av. Montes de Oca 352");

    // ENVIOS
    Envio envio1= new Carta(persona1,empresa1,"simple","rojo");
    Envio envio2= new Encomienda(persona3,empresa2,"packets");
    Envio envio3= new Carta(persona1,persona3,"certificada","negro");

    // Movimientos
        ArrayList<Sucursal> sucursalesVisitadas1 = new ArrayList();

        Movimiento mov1= new Movimiento(envio1,sucursalesVisitadas1,false);
        mov1.iniciarEnvio(envio1,emp1,0);
        mov1.agregarParada(suc2);
        mov1.actualizarEstado();

        ArrayList<Sucursal> sucursalesVisitadas2 = new ArrayList();
        Movimiento mov2= new Movimiento(envio2,sucursalesVisitadas2,false);
        mov2.iniciarEnvio(envio1,emp2,1);
        mov2.agregarParada(suc1);
        mov2.actualizarEstado();
        System.out.println(mov2.entregado);
        System.out.println(mov1.envio.precio);
        System.out.println(mov1.sucursalesVisitadas.size());

    }}

