package org.example.Dominio.Reportes;

public class GeneradorReporteCantidadViandasColaborador implements GeneradorDeReportes{

    private static GeneradorReporteCantidadViandasColaborador instance;
    private int cantidadDeViandas;
    private int cantidadColaboradores;

    private GeneradorReporteCantidadViandasColaborador(){}

    public static GeneradorReporteCantidadViandasColaborador getInstance(){
        if(instance == null){
            instance = new GeneradorReporteCantidadViandasColaborador();
        }
        return instance;
    }

    public void update() {
        this.cantidadDeViandas++;
    }

    public void generarReporte() {
        //return (double)cantidadDeViandas/cantidadColaboradores;
    }

    public boolean isTypeOf(TipoReporte tipo) {
        return tipo == TipoReporte.VIANDAS_COLABORADOR;
    }
}
