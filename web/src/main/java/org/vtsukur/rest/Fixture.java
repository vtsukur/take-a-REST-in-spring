package org.vtsukur.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vtsukur.rest.core.domain.Hotel;
import org.vtsukur.rest.core.domain.HotelRepository;
import org.vtsukur.rest.core.domain.Room;
import org.vtsukur.rest.core.domain.RoomRepository;

import java.util.Set;

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
        roomRepository.deleteAll();
        hotelRepository.deleteAll();

        saveNobilis();
        saveLeopolis();
    }

    private Hotel saveNobilis() {
        nobilis = hotelRepository.save(new Hotel("Nobilis"));
        addRoom(nobilis, roomRepository.save(new Room(nobilis, "Standard")));
        return nobilis;
    }

    private static void addRoom(final Hotel hotel, final Room room) {
        addRoom(hotel.getRooms(), room);
    }

    private static void addRoom(final Set<Room> rooms, final Room room) {
        rooms.add(room);
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
