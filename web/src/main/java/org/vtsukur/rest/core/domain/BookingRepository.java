package org.vtsukur.rest.core.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author volodymyr.tsukur
 */
@RepositoryRestResource
public interface BookingRepository extends PagingAndSortingRepository<Booking, Long> {}
