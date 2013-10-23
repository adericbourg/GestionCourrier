package controllers.metrics;

import com.codahale.metrics.MetricRegistry;

/**
 *
 */
public class Metrics {

    private static final MetricRegistry REGISTRY = new MetricRegistry();

    public static MetricRegistry getRegistry() {
        return REGISTRY;
    }
}
