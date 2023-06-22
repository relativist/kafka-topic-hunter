package ru.element.lab.dedup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        log.info("VERSION: 6Jun 21:31");
        SpringApplication.run(Application.class, args);
    }
}
