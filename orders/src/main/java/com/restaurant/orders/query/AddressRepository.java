package com.restaurant.orders.query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface AddressRepository extends CrudRepository<Address, String> {

    @RestResource(exported = false)
    Address save(Address item);

    @RestResource(exported = false)
    void delete(Address item);
}
