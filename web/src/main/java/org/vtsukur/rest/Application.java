package org.vtsukur.rest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author volodymyr.tsukur
 */
@SpringBootApplication
public class Application {

    @Bean
    CommandLineRunner init(final Fixture fixture) {
        return (event) -> fixture.init();
    }

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
