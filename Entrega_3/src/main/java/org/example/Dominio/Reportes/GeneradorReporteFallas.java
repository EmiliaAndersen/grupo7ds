package org.example.Dominio.Reportes;

public class GeneradorReporteFallas implements GeneradorDeReportes{

    private int cantidadDeFallas;
    private int cantidadDeHeladeras;
    private static GeneradorReporteFallas instance;

    private GeneradorReporteFallas() {}

    public static GeneradorReporteFallas getInstance(){
        if(instance == null){
            instance = new GeneradorReporteFallas();
        }
        return instance;
    }

    public void update() {
        this.cantidadDeFallas++;
    }

    public double obtenerReporte() {
        return (double) this.cantidadDeFallas / this.cantidadDeHeladeras;
    }

    public boolean isTypeOf(TipoReporte tipo) {
        return tipo == TipoReporte.FALLAS;
    }
}
