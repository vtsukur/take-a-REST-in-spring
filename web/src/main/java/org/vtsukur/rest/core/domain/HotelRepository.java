package org.vtsukur.rest.core.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author volodymyr.tsukur
 */
@RepositoryRestResource
public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long> {

    @RestResource(exported = false)
    @Override
    <S extends Hotel> S save(S entity);

    @RestResource(exported = false)
    @Override
    <S extends Hotel> Iterable<S> save(Iterable<S> entities);

    Page<Hotel> findByCity(@Param("city") String city, Pageable pageable);

}
