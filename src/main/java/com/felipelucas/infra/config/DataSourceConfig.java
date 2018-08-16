package com.felipelucas.infra.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hikari.datasource")
public class DataSourceConfig extends HikariConfig {

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(this);
    }
}