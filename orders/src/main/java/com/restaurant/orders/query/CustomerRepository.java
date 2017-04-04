package com.restaurant.orders.query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface CustomerRepository extends CrudRepository<Customer, String> {

    @RestResource(exported = false)
    Customer save(Customer item);

    @RestResource(exported = false)
    void delete(Customer item);
}
