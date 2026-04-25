package jota.developer.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@AllArgsConstructor
public class ConnectionBeanConfiguration {
    private final ConnectionConfigurationProperties configurationProperties;

    @Bean
//    @Profile("mysql")
    @Primary
    public Connection connectionMySql() {
        return new Connection(
                configurationProperties.url(),
                configurationProperties.username(),
                configurationProperties.password());
    }
}
