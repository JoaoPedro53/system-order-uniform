package jota.developer.config;

import external.depedency.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfiguration {
    @Value("${database.mysql.url}")
    private String url;
    @Value("${database.mysql.username}")
    private String username;
    @Value("${database.mysql.password}")
    private String password;


    @Bean
    public Connection connectionMySql() {return new Connection(url,username,password);}
}
