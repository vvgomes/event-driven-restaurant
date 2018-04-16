package com.restaurant.menu.command.api;

import com.restaurant.menu.command.ModifyMenuItemCommand;
import com.restaurant.menu.command.RemoveMenuItemCommand;
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

import java.math.BigDecimal;
import java.util.concurrent.Future;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/menu/items/{id}/commands")
@ResponseStatus(HttpStatus.ACCEPTED)
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MenuItemCommandsController implements ResourceProcessor<RepositoryLinksResource> {

    @Autowired
    CommandGateway commandGateway;

    @PostMapping("/modify")
    public Future<?> modifyMenuItem(@PathVariable String id, @RequestBody MenuItemRequestBody body) {
        String description = body.getDescription();
        BigDecimal price = body.getPrice();

        FutureCallback<ModifyMenuItemCommand, Object> callback = new FutureCallback<>();
        commandGateway.send(new ModifyMenuItemCommand(id, description, price), callback);
        return callback.toCompletableFuture();
    }

    @PostMapping("/remove")
    public Future<?> removeMenuItem(@PathVariable String id) {
        FutureCallback<ModifyMenuItemCommand, Object> callback = new FutureCallback<>();
        commandGateway.send(new RemoveMenuItemCommand(id));
        return callback.toCompletableFuture();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResourceSupport> getCommands(@PathVariable String id) {
        ResourceSupport commands = new ResourceSupport();

        commands.add(linkTo(methodOn(getClass()).modifyMenuItem(id, null)).withRel("modify").expand(id));
        commands.add(linkTo(methodOn(getClass()).removeMenuItem(id)).withRel("remove").expand(id));

        return ResponseEntity.ok(commands);
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        return resource;
    }
}
