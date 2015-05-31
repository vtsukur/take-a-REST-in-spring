package org.vtsukur.rest.styles.hypermedia;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.vtsukur.rest.core.domain.City;

/**
 * @author volodymyr.tsukur
 */
public class CityResourceAssembler extends ResourceAssemblerSupport<City, CityResource> {

    public CityResourceAssembler() {
        super(CityResourceController.class, CityResource.class);
    }

    @Override
    public CityResource toResource(City entity) {
        CityResource cityResource = createResourceWithId(entity.getId(), entity);
        cityResource.setName(entity.getKey());
        return cityResource;
    }

}
