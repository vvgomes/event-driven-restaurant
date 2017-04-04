package com.restaurant.customers.query;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface CustomerRepository extends PagingAndSortingRepository<Customer, String> {

    @RestResource(exported = false)
    Customer save(Customer customer);

    @RestResource(exported = false)
    void delete(Customer customer);
}
