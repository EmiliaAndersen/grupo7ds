package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.example.Servicio.MetricsService;
import org.jetbrains.annotations.NotNull;

public class GetMicrometerMetrics implements Handler {

    @Override
    public void handle(@NotNull Context context) throws Exception {
        context.result(MetricsService.scrapeRegistry());
    }
}
