package org.example.Dominio.Reportes;

public interface GeneradorDeReportes {
    public void update();

    public double obtenerReporte();

    public boolean isTypeOf(TipoReporte tipo);
}
