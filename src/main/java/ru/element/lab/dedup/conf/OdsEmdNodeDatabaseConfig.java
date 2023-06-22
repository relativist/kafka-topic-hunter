package ru.element.lab.dedup.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Параметры подключения к ODS MAPPING.
 */
@Getter
@Setter
@Configuration
public class OdsEmdNodeDatabaseConfig {
    /**
     * Драйвер.
     */
    @Value("${ods-emd-node.source-driver}")
    private String driver;

    /**
     * Юзер для базы.
     */
    @Value("${ods-emd-node.source-user}")
    private String user;

    /**
     * Пароль для базы.
     */
    @Value("${ods-emd-node.source-password}")
    private String password;

    /**
     * Адрес базы с таблицей.
     */
    @Value("${ods-emd-node.source-url}")
    private String url;

    /**
     * Бин источника для базы.
     */
    @Bean("ods-emd-node-ds")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(url)
                .driverClassName(driver)
                .username(user)
                .password(password)
                .build();
    }

    /**
     * Бин для работы с базой.
     */
    @Bean("ods-emd-node-template")
    public JdbcTemplate jdbcTemplate(@Qualifier("ods-emd-node-ds") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
