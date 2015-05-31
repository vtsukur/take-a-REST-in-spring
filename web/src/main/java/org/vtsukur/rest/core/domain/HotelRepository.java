package org.vtsukur.rest.core.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
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

}
