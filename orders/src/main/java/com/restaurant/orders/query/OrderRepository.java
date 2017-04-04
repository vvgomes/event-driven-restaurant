package com.restaurant.orders.query;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface OrderRepository extends PagingAndSortingRepository<Order, String> {

    @RestResource(exported = false)
    Order save(Order order);

    @RestResource(exported = false)
    void delete(Order order);
}
