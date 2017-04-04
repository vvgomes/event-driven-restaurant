package com.restaurant.customers.query;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface AddressRepository extends PagingAndSortingRepository<Address, String> {

    @RestResource(exported = false)
    Address save(Address address);

    @RestResource(exported = false)
    void delete(Address address);
}
