package org.example.Servicio;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.example.Dominio.PuntosEstrategicos.PuntoGeografico;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class UbicacionService {
    private static final String API_BASE_URL = Config.get("api.ubicacion.base.url") + "/api/points/nearby";

    public List<PuntoGeografico> getNearbyPoints(double latitude, double longitude, int radioKm) throws Exception {
        // Convert latitude and longitude to strings and sanitize them
        String sanitizedLatitude = String.valueOf(latitude).replace(",", ".");
        String sanitizedLongitude = String.valueOf(longitude).replace(",", ".");

        String apiUrl = String.format("%s?latitude=%s&longitude=%s&radioKm=%d",
                API_BASE_URL, sanitizedLatitude, sanitizedLongitude, radioKm);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Map response to a list of PuntoGeografico
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<>() {});
    }
}




class Config {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("UbicacionService.properties")) {
            if (input == null) {
                throw new IOException("Configuration file not found!");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load configuration: " + ex.getMessage(), ex);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}