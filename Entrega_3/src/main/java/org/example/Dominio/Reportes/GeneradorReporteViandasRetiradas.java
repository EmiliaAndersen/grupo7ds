package org.example.Dominio.Reportes;

public class GeneradorReporteViandasRetiradas implements GeneradorDeReportes{

    private static GeneradorReporteViandasRetiradas instance;
    private int cantidadDeViandasRetiradas;
    private int cantidadDeHeladeras;

    private GeneradorReporteViandasRetiradas() {}

    public static GeneradorReporteViandasRetiradas getInstance() {
        if(instance == null)
        {
            instance = new GeneradorReporteViandasRetiradas();
        }
        return instance;
    }

    public void update() {
        this.cantidadDeViandasRetiradas++;
    }

    public void generarReporte() {
        //return (double) this.cantidadDeViandasRetiradas/this.cantidadDeHeladeras;
    }

    public boolean isTypeOf(TipoReporte tipo) {
        return tipo == TipoReporte.VIANDAS_RETIRADAS;
    }
}
