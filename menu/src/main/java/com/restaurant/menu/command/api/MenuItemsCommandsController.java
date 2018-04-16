package com.restaurant.menu.command.api;

import com.restaurant.menu.command.AddMenuItemCommand;
import com.restaurant.menu.query.Item;
import lombok.AllArgsConstructor;
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
import java.util.concurrent.Future;

import static java.util.UUID.randomUUID;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/menu/items/commands")
@ResponseStatus(HttpStatus.ACCEPTED)
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MenuItemsCommandsController implements ResourceProcessor<RepositoryLinksResource> {

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    EntityLinks links;

    @PostMapping("/add")
    public Future<?> addMenuItem(@RequestBody MenuItemRequestBody body) {
        String id = randomUUID().toString();
        String description = body.getDescription();
        BigDecimal price = body.getPrice();

        FutureCallback<AddMenuItemCommand, Object> callback = new FutureCallback<>();
        commandGateway.send(new AddMenuItemCommand(id, description, price), callback);

        return callback
            .thenApply(v -> links.linkForSingleResource(Item.class, id).withSelfRel().getHref())
            .toCompletableFuture();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResourceSupport> getCommands() {
        ResourceSupport commands = new ResourceSupport();
        commands.add(linkTo(methodOn(getClass()).addMenuItem(null)).withRel("add"));
        return ResponseEntity.ok(commands);
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        return resource;
    }
}
