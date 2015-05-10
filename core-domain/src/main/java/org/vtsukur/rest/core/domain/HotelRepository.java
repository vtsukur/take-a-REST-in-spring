package org.vtsukur.rest.core.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author volodymyr.tsukur
 */
@RepositoryRestResource
public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long> {

    @Override
    @RestResource(exported = false)
    void delete(Long aLong);

    @Override
    @RestResource(exported = false)
    void delete(Hotel entity);

}
