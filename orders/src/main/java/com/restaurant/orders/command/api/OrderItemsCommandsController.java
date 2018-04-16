package com.restaurant.orders.command.api;

import com.restaurant.orders.command.AddItemToOrderCommand;
import com.restaurant.orders.config.ResourceURI;
import com.restaurant.orders.query.MenuItemRepository;
import com.restaurant.orders.query.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.callbacks.FutureCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.Future;

import static java.util.UUID.randomUUID;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders/{orderId}/orderItems/commands")
@ResponseStatus(HttpStatus.ACCEPTED)
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderItemsCommandsController implements ResourceProcessor<RepositoryLinksResource> {

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    MenuItemRepository menuItems;

    @Autowired
    EntityLinks links;

    @PostMapping("/add")
    public Future<?> add(@PathVariable String orderId, @RequestBody OrderItemRequestBody body) {
        String menuItemId = new ResourceURI(body.getMenuItem()).getId();
        String orderItemId = randomUUID().toString();
        Integer quantity = body.getQuantity();

        BigDecimal price = Optional
            .ofNullable(menuItems.findOne(menuItemId))
            .orElseThrow(Exceptions.MenuItemNotFoundException::new)
            .getPrice();

        FutureCallback<AddItemToOrderCommand, Object> callback = new FutureCallback<>();
        commandGateway.send(new AddItemToOrderCommand(orderId, orderItemId, menuItemId, price, quantity), callback);

        return callback
            .thenApply(v -> links.linkForSingleResource(OrderItem.class, orderItemId).withSelfRel().getHref())
            .toCompletableFuture();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResourceSupport> getCommands(@PathVariable String orderId) {
        ResourceSupport commands = new ResourceSupport();
        commands.add(linkTo(methodOn(getClass()).add(orderId, null)).withRel("add"));
        return ResponseEntity.ok(commands);
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        return resource;
    }

    @Data
    @AllArgsConstructor
    private static class OrderItemRequestBody {
        String menuItem;
        Integer quantity;
    }
}
