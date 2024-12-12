package org.example.Handlers;

public class GoogleAuthRequest {

    private String credential;  // Aquí guardas el token de Google

    // Constructor vacío
    public GoogleAuthRequest() {
    }

    // Constructor con credenciales
    public GoogleAuthRequest(String credential) {
        this.credential = credential;
    }

    // Getter y Setter para la credencial
    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }
}
