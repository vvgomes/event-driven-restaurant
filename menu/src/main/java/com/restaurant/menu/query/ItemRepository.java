package com.restaurant.menu.query;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface ItemRepository extends PagingAndSortingRepository<Item, String> {

    @RestResource(exported = false)
    Item save(Item item);

    @RestResource(exported = false)
    void delete(Item item);
}
