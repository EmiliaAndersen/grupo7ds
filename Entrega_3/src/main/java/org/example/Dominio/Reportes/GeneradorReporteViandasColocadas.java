package org.example.Dominio.Reportes;

public class GeneradorReporteViandasColocadas implements GeneradorDeReportes{

    private static GeneradorReporteViandasColocadas instance;
    private int cantidadDeViandasColocadas;
    private int cantidadDeHeladeras;

    private GeneradorReporteViandasColocadas(){}

    public static GeneradorReporteViandasColocadas getInstance(){
        if(instance == null){
            instance = new GeneradorReporteViandasColocadas();
        }
        return instance;
    }

    public void update() {
        this.cantidadDeViandasColocadas++;
    }

    public void generarReporte() {
        //return (double) this.cantidadDeViandasColocadas/this.cantidadDeHeladeras;
    }

    public boolean isTypeOf(TipoReporte tipo) {
        return tipo == TipoReporte.VIANDAS_COLOCADAS;
    }
}
