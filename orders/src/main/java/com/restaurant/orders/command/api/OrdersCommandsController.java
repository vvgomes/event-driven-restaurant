package com.restaurant.orders.command.api;

import com.restaurant.orders.command.OpenOrderCommand;
import com.restaurant.orders.config.ResourceURI;
import com.restaurant.orders.query.CustomerRepository;
import com.restaurant.orders.query.Order;
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

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;

import static com.restaurant.orders.command.api.Exceptions.CustomerNotFoundException;
import static java.util.UUID.randomUUID;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders/commands")
@ResponseStatus(HttpStatus.ACCEPTED)
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrdersCommandsController implements ResourceProcessor<RepositoryLinksResource> {

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    CustomerRepository customers;

    @Autowired
    EntityLinks links;

    @PostMapping("/open")
    public Future<?> open(@RequestBody Map<String, String> body) {
        String id = randomUUID().toString();
        String customerId = new ResourceURI(body.get("customer")).getId();

        Optional
            .ofNullable(this.customers.findOne(customerId))
            .orElseThrow(CustomerNotFoundException::new);

        FutureCallback<OpenOrderCommand, Object> callback = new FutureCallback<>();
        commandGateway.send(new OpenOrderCommand(id, customerId), callback);

        return callback
            .thenApply(v -> links.linkForSingleResource(Order.class, id).withSelfRel().getHref())
            .toCompletableFuture();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResourceSupport> getCommands() {
        ResourceSupport commands = new ResourceSupport();
        commands.add(linkTo(methodOn(getClass()).open(null)).withRel("open"));
        return ResponseEntity.ok(commands);
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        return resource;
    }
}
