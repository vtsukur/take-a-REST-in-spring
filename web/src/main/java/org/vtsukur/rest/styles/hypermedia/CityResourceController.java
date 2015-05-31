package org.vtsukur.rest.styles.hypermedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.vtsukur.rest.core.domain.City;

import java.util.Arrays;

/**
 * @author volodymyr.tsukur
 */
@RestController
@RequestMapping("/cities")
@ExposesResourceFor(City.class)
public class CityResourceController {

    @Autowired
    private EntityLinks entityLinks;

    private CityResourceAssembler assembler = new CityResourceAssembler();

    @RequestMapping(method = RequestMethod.GET)
    public Object all() {
        return assembler.toResources(Arrays.asList(getLviv(), getKyiv()));
    }

    @RequestMapping(value = "{name}", method = RequestMethod.GET)
    public Object one(@PathVariable("name") String id) {
        return assembler.toResource(getLviv());
    }

    @RequestMapping(value = "meta", method = RequestMethod.GET)
    public ResourceSupport links() {
        ResourceSupport links = new ResourceSupport();
        links.add(entityLinks.linkToCollectionResource(City.class).withRel("cities"));
        links.add(entityLinks.linkToSingleResource(City.class, 1L).withRel("city"));
        return links;
    }

    private City getLviv() {
        return new City("Lviv", 300);
    }

    private City getKyiv() {
        return new City("Kyiv", 500);
    }

}
