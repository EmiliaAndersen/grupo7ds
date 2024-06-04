package org.example.Dominio.Tecnicos;

import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;

public class AreaCobertura {
    private float longitudCentro;
    private float latitudCentro;
    private float radio;

    public boolean areaCubierta(PuntoEstrategico area){
        return this.respetaLongitud(area) && this.respetaLatitud(area);
    }

    private boolean respetaLongitud(PuntoEstrategico puntoEstrategico){
        return puntoEstrategico.longitud() <= longitudCentro + radio;
    }

    private boolean respetaLatitud(PuntoEstrategico puntoEstrategico){
        return puntoEstrategico.latitud() <= latitudCentro + radio;
    }
}
