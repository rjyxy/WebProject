package top.yxy.server.config;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MetricsConfig {

    @Bean
    public MeterBinder initDate(Environment env) {
        return (registry) -> Gauge.builder("init.date", this::date).register(registry);
    }

    @Bean
    public MeterBinder systemDate(Environment env) {
        return (registry) -> Gauge.builder("system.date", this::date).register(registry);
    }

    private Number date() {
        return 2024.11;
    }

}