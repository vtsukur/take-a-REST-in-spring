package org.vtsukur.rest;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.vtsukur.rest.core.domain.Booking;

/**
 * @author volodymyr.tsukur
 */
@RepositoryRestResource(path = "/api/hotels")
public interface HotelRepository extends PagingAndSortingRepository<Booking, Long> {}
