package jota.developer;

import jota.developer.config.ConnectionConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConnectionConfigurationProperties.class)
public class SystemOrderUniformApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemOrderUniformApplication.class, args);
    }

}
