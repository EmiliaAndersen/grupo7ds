package org.example.Servicio;

import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusMeterRegistry;

public class MetricsService {
    public static PrometheusMeterRegistry registry = new PrometheusMeterRegistry(io.micrometer.prometheus.PrometheusConfig.DEFAULT);
    public static Counter requestCounter = registry.counter("Login_counter");

    private static MetricsService instance;

    private MetricsService() {}

    public static MetricsService getInstance(){
        if (instance == null) {
            instance = new MetricsService();
        }
        return instance;
    }


    public static void incrementRequestCounter(){
        requestCounter.increment();
    }

    public static String scrapeRegistry(){
        return registry.scrape();
    }
}
