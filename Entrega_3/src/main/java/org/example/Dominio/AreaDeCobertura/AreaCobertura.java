package org.example.Dominio.AreaDeCobertura;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;

import javax.persistence.*;

@Entity
@Table
public class AreaCobertura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column
    private double longitudCentro;
    @Setter
    @Getter
    @Column
    private double latitudCentro;
    @Setter
    @Getter
    @Column
    private double radio;

    public boolean areaCubierta(PuntoEstrategico area) {
        return this.respetaLongitud(area) && this.respetaLatitud(area);
    }

    private boolean respetaLongitud(PuntoEstrategico puntoEstrategico){
        return puntoEstrategico.longitud() <= longitudCentro + radio;
    }

    private boolean respetaLatitud(PuntoEstrategico puntoEstrategico){
        return puntoEstrategico.latitud() <= latitudCentro + radio;
    }
}
