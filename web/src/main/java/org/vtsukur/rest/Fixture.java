package org.vtsukur.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vtsukur.rest.core.domain.Hotel;
import org.vtsukur.rest.core.domain.HotelRepository;
import org.vtsukur.rest.core.domain.Room;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author volodymyr.tsukur
 */
@Component
public class Fixture {

    @Autowired
    private HotelRepository hotelRepository;

    private Hotel nobilis;

    private Hotel leopolis;

    public void init() {
        hotelRepository.deleteAll();

        saveNobilis();
        saveLeopolis();
    }

    private Hotel saveNobilis() {
        nobilis = new Hotel("Nobilis");
        nobilis.setRooms(new HashSet<>(Arrays.asList(new Room(nobilis, "Standard"))));
        nobilis = hotelRepository.save(nobilis);
        return nobilis;
    }

    private Hotel saveLeopolis() {
        leopolis = hotelRepository.save(new Hotel("Leopolis"));
        return leopolis;
    }

    public Hotel getNobilis() {
        return nobilis;
    }

    public Hotel getLeopolis() {
        return leopolis;
    }

}
