package com.restaurant.orders.query;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, String> {

    @RestResource(exported = false)
    OrderItem save(OrderItem item);

    @RestResource(exported = false)
    void delete(OrderItem item);
}
