package com.restaurant.orders.command.api;

import com.restaurant.orders.command.RemoveItemFromOrderCommand;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.callbacks.FutureCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders/{orderId}/orderItems/{id}/commands")
@ResponseStatus(HttpStatus.ACCEPTED)
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderItemCommandsController implements ResourceProcessor<RepositoryLinksResource> {

    @Autowired
    CommandGateway commandGateway;

    @PostMapping("/remove")
    public Future<?> remove(@PathVariable String orderId, @PathVariable String id) {
        FutureCallback<RemoveItemFromOrderCommand, Object> callback = new FutureCallback<>();
        commandGateway.send(new RemoveItemFromOrderCommand(orderId, id), callback);
        return callback.toCompletableFuture();
    }

    @PostMapping("/change-quantity")
    public Future<?> changeQuantity(
        @PathVariable String orderId, @PathVariable String id,
        @RequestBody Map<String, Integer> body
    ) {
        return new CompletableFuture<>();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResourceSupport> getCommands(@PathVariable String orderId, @PathVariable String id) {
        ResourceSupport commands = new ResourceSupport();

        commands.add(linkTo(methodOn(getClass()).remove(orderId, id))
            .withRel("remove").expand(orderId, id));

        commands.add(linkTo(methodOn(getClass()).changeQuantity(orderId, id, null))
            .withRel("change-quantity").expand(orderId, id));

        return ResponseEntity.ok(commands);
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        return resource;
    }
}
