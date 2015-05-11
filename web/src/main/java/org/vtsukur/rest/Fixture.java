package org.vtsukur.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vtsukur.rest.core.domain.Hotel;
import org.vtsukur.rest.core.domain.HotelRepository;
import org.vtsukur.rest.core.domain.Room;
import org.vtsukur.rest.core.domain.RoomRepository;

/**
 * @author volodymyr.tsukur
 */
@Component
public class Fixture {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    private Hotel nobilis;

    private Hotel leopolis;

    public void init() {
        hotelRepository.deleteAll();

        saveNobilis();
        saveLeopolis();
    }

    private Hotel saveNobilis() {
        final Hotel target = hotelRepository.save(new Hotel("Nobilis"));
        roomRepository.save(new Room(target, "Standard"));
        nobilis = hotelRepository.findOne(target.getId());
        return target;
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
