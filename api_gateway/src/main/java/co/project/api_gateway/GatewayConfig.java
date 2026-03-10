package co.project.api_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class GatewayConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            // Rutas actuator health
            .route("auth-health", r -> r
                .path("/auth/actuator/health")
                .filters(f -> f.rewritePath("/auth/actuator/health", "/actuator/health"))
                .uri("http://api-auth:8082"))
            .route("catalogo-health", r -> r
                .path("/api/catalogo/actuator/health")
                .filters(f -> f.rewritePath("/api/catalogo/actuator/health", "/actuator/health"))
                .uri("http://api-catalogo:8081"))
            .route("pedidos-health", r -> r
                .path("/api/pedidos/actuator/health")
                .filters(f -> f.rewritePath("/api/pedidos/actuator/health", "/actuator/health"))
                .uri("http://api-pedidos:8083"))
            // Rutas principales
            .route("auth-service", r -> r
                .path("/auth/**")
                .uri("http://api-auth:8082"))
            .route("pedidos-service", r -> r
                .path("/api/pedidos/**")
                .uri("http://api-pedidos:8083"))
            .route("catalogo-service", r -> r
                .path("/api/catalogo/**")
                .uri("http://api-catalogo:8081"))
            .build();
    }
}