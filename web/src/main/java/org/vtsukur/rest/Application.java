package org.vtsukur.rest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.vtsukur.rest.core.domain.Hotel;
import org.vtsukur.rest.core.domain.HotelRepository;

/**
 * @author volodymyr.tsukur
 */
@SpringBootApplication
public class Application {

    @Bean
    CommandLineRunner init(final HotelRepository hotelRepository) {
        return (event) -> {
            hotelRepository.save(new Hotel("Rius"));
            hotelRepository.save(new Hotel("Nobilis"));
            hotelRepository.save(new Hotel("Leopolis"));
        };
    }

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
