package org.vtsukur.rest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.DefaultCurieProvider;

/**
 * @author volodymyr.tsukur
 */
@SpringBootApplication
public class Application {

    @Bean
    CommandLineRunner init(final Fixture fixture) {
        return (event) -> fixture.init();
    }

    @Bean
    CurieProvider curieProvider() {
        return new DefaultCurieProvider("take-a-rest", new UriTemplate("http://localhost:8080/api/doc/{rel}"));
    }

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
