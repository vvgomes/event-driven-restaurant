package com.restaurant.customers.config;

import com.restaurant.customers.query.Address;
import com.restaurant.customers.query.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class AddressResourceProcessor implements ResourceProcessor<Resource<Address>> {

    @Autowired
    private RepositoryRestMvcConfiguration configuration;

    @Override
    public Resource<Address> process(Resource<Address> resource) {
        Address address = resource.getContent();
        String addressId = address.getId();
        String customerId = address.getCustomer().getId();
        //resource.add(linkTo(methodOn(CustomerCommandsController.class).getCommands(id)).withRel("commands"));

        String customerHref = customerHref(customerId);
        String addressHref = addressHref(addressId, customerHref);
        return resource;
    }

    private String addressHref(String addressId, String customerHref) {
        return customerHref.concat(format("/addresses/%s", addressId));
    }

    private String customerHref(String customerId) {
        return configuration.entityLinks()
            .linkToSingleResource(Customer.class, customerId)
            .withSelfRel().getHref();
    }
}

