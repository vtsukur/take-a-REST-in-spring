package org.vtsukur.rest;

import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.vtsukur.rest.core.domain.*;
import org.vtsukur.rest.etc.money.Currencies;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author volodymyr.tsukur
 */
@Configuration
public class Fixture {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    private Hotel nobilis;

    private Hotel leopolis;

    public void init() {
        bookingRepository.deleteAll();
        roomRepository.deleteAll();
        hotelRepository.deleteAll();

        saveNobilis();
        saveLeopolis();
    }

    private Hotel saveNobilis() {
        nobilis = new Hotel(
                "Nobilis",
                Hotel.PropertyType.HOTEL,
                164,
                new Rating(new BigDecimal(9.4), 246),
                new Address("Ukraine", "Lviv", 79005, "O.Fredra Street 5"));
        addRoom(nobilis, roomRepository.save(new Room("Standard", Money.of(143, Currencies.USD))));
        nobilis = hotelRepository.save(nobilis);
        return nobilis;
    }

    private static void addRoom(final Hotel hotel, final Room room) {
        addRoom(hotel.getRooms(), room);
    }

    private static void addRoom(final Set<Room> rooms, final Room room) {
        rooms.add(room);
    }

    private Hotel saveLeopolis() {
        leopolis = hotelRepository.save(
                new Hotel(
                        "Leopolis",
                        Hotel.PropertyType.HOTEL,
                        81,
                        new Rating(new BigDecimal(9.2), 125),
                        new Address("Ukraine", "Lviv", 79008, "Teatralna Street 16")));
        return leopolis;
    }

    public Hotel getNobilis() {
        return nobilis;
    }

    public Hotel getLeopolis() {
        return leopolis;
    }

}
