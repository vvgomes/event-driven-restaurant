package com.restaurant.orders.query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface MenuItemRepository extends CrudRepository<MenuItem, String> {

    @RestResource(exported = false)
    MenuItem save(MenuItem item);

    @RestResource(exported = false)
    void delete(MenuItem item);
}
