package org.example.Dominio.Tarjetas;

import lombok.Getter;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.PersonaVulnerable.PersonaVulnerable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tarjeta {
    private String codigo;
    @Getter
    private List<UsoTarjeta> usos;
    private PersonaVulnerable personaVulnerable;

    public Tarjeta(PersonaVulnerable unaPersonaVulnerable){
        this.codigo = this.generarCodigo();
        this.usos = new ArrayList<UsoTarjeta>();
        this.personaVulnerable = unaPersonaVulnerable;
    }

    private String generarCodigo(){
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijoklmnopqrstuvwxyz1234567890";
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < 20; i++) {
            int index = random.nextInt(caracteres.length());
            codigo.append(caracteres.charAt(index));
        }
        return codigo.toString();
    }

    public void registrarUso(Heladera unaHeladera) {
        if (this.validarUso(personaVulnerable.getMenoresACargo())) {
            if(unaHeladera.retirarVianda()){
                UsoTarjeta nuevoUso = new UsoTarjeta(LocalDate.now(), LocalTime.now(), unaHeladera);
                usos.add(nuevoUso);
            }
            else{
                System.out.println("La heladera no tiene mas viandas disponibles");
            }
        } else {
            System.out.println("Limite de usos diarios alcanzado para la tarjeta: " + codigo);
        }
    }

    private boolean validarUso(int menoresACargo){
        List<UsoTarjeta> usosHoy = this.usosDe(LocalDate.now());
        return usosHoy.size() < 4 + 2*menoresACargo;
    }

    private List<UsoTarjeta> usosDe(LocalDate unaFecha){
        return usos.stream().filter(c -> c.getFecha().isEqual(unaFecha)).toList();
    }
}
