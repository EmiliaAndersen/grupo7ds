package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.jetbrains.annotations.NotNull;

public class GetMicrometerMetrics implements Handler {

    private final PrometheusMeterRegistry prometheusRegistry;

    public GetMicrometerMetrics(PrometheusMeterRegistry prometheusRegistry) {
        this.prometheusRegistry = prometheusRegistry;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        context.result(prometheusRegistry.scrape());
    }
}
