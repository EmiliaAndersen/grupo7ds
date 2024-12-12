package org.example.Servicio;

import io.javalin.http.Context;
import org.example.Dominio.Heladeras.Heladera;
import org.example.repositorios.RepositorioHeladeras;

import java.util.List;
import java.util.stream.Collectors;

public class RecomendarHeladerasService {
    private static final double EARTH_RADIUS_KM = 6371.0;

    /**
     * API endpoint to recommend "heladeras" within a given radius of a point.
     *
     * @param ctx the Javalin context containing query parameters: latitude, longitude, and radius.
     */
    public static void apiRecomendacion(Context ctx) {
        try {
            // Parse query parameters with proper validation
            double latitud = parseDoubleParam(ctx.queryParam("latitude"), "latitude");
            double longitud = parseDoubleParam(ctx.queryParam("longitude"), "longitude");
            double radio = parseDoubleParam(ctx.queryParam("radio"), "radio");

            if (radio <= 0) {
                ctx.status(400).result("Radio must be greater than zero.");
                return;
            }

            // Fetch all heladeras
            RepositorioHeladeras repoHeladeras = new RepositorioHeladeras();
            List<Heladera> heladeras = repoHeladeras.obtenerTodasHeladeras();

            // Filter heladeras within the specified radius
            List<Heladera> heladerasFiltradas = heladeras.stream()
                    .filter(heladera -> validoRecomendacion(
                            heladera.getUbicacion().latitud(),
                            heladera.getUbicacion().longitud(),
                            latitud,
                            longitud,
                            radio
                    ))
                    .collect(Collectors.toList());

            // Simulate processing delay (optional)
            simularDemora();

            // Respond with filtered heladeras
            ctx.json(heladerasFiltradas);
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Internal server error: " + e.getMessage());
        }
    }


    private static double parseDoubleParam(String paramValue, String paramName) {
        if (paramValue == null || paramValue.isEmpty()) {
            throw new IllegalArgumentException("Missing required query parameter: " + paramName);
        }
        try {
            return Double.parseDouble(paramValue);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format for parameter: " + paramName);
        }
    }


    private static void simularDemora() {
        try {
            Thread.sleep(3000); // 1-second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Checks if a point is within the specified radius of a center point.
     *
     * @param pointLat  Latitude of the point.
     * @param pointLon  Longitude of the point.
     * @param centerLat Latitude of the center.
     * @param centerLon Longitude of the center.
     * @param radiusKm  Radius in kilometers.
     * @return true if the point is within the radius, false otherwise.
     */
    private static boolean validoRecomendacion(double pointLat, double pointLon,
                                          double centerLat, double centerLon,
                                          double radiusKm) {
        double latDistance = Math.toRadians(pointLat - centerLat);
        double lonDistance = Math.toRadians(pointLon - centerLon);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(centerLat)) * Math.cos(Math.toRadians(pointLat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS_KM * c;

        return distance <= radiusKm;
    }
}