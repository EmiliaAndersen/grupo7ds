package org.example.validador;

import org.example.Validador.ControlPasswordDebil;
import org.example.Validador.Nist800;
import org.example.Validador.Usuario;
import org.example.Validador.Validador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ValidadorTests {

    private static Validador validador;
    private static Nist800 nist800;
    private static ControlPasswordDebil controlPasswordDebil;

    @BeforeAll
    public static void setUp() throws Exception {
        validador = new Validador();
        nist800 = new Nist800();
        controlPasswordDebil = new ControlPasswordDebil();
    }

    @Test
    public void usuario_creacionUsuario(){
        Usuario usuario = new Usuario();
        usuario.setUsuario("Carlos");

        Assertions.assertArrayEquals(usuario.getUsuario().toCharArray(), "Carlos".toCharArray());
        // Se agrega un nombre de usuario de forma correcta al usuario
    }

    @Test
    public void usuario_setContrasenia() {
        Usuario usuario = new Usuario();
        usuario.setContrasenia("contraseniaBuena");

        Assertions.assertArrayEquals(usuario.getContrasenia().toCharArray(), "contraseniaBuena".toCharArray());
        // Se agrega una contraseña de forma correcta al usuario
    }

    @Test
    public void validador_inicializar_cantidadCorrectaDeFiltros() {
        validador.addFiltro(nist800);
        validador.addFiltro(controlPasswordDebil);

        Assertions.assertEquals(2, validador.getFiltros().size());
        // Se agregaron correctamente 2 filtros
    }

    @Test
    public void validador_validar_contraseniaFuerte() {
        boolean resultado = validador.validarPassword("contraseniaBuena1");

        Assertions.assertTrue(resultado);
        // La contraseña es buena
    }

    @Test
    public void validador_validar_contraseniaDebil() {
        boolean resultado = validador.validarPassword("1234");

        Assertions.assertFalse(resultado);
        // La contraseña es debil
    }

}
