# Event Driven Restaurant

This project demonstrate the use of [Event Sourcing](https://martinfowler.com/eaaDev/EventSourcing.html) and [CQRS](https://martinfowler.com/bliki/CQRS.html) in the context of a [Microservices](https://martinfowler.com/articles/microservices.html)-based system. It consists of a very simple online restaurant application composed by three services: Menu, Customers, and Orders. The intention is to demonstrate the architecture proposed in this talk: [When Microservices meet Event Sourcing](https://speakerdeck.com/vvgomes/when-microservices-meet-event-sourcing), presented at:

- [The Developers Conference - Sao Paulo 2016](http://thedevconf.com.br/tdc/2016/saopaulo/trilha-microservices)
- [O'Reilly Software Architecture Conference - New York 2017](https://conferences.oreilly.com/software-architecture/sa-ny/public/schedule/detail/56806)
- [ThoughtWorks Tech Talks Meetup - New York 2017](https://www.meetup.com/ThoughtWorks-Tech-Talks-NYC/events/239465465/)
- [Developer Week - New York 2017](https://developerweekny2017.sched.com/event/9yzS/when-microservices-meet-event-sourcing)
- [QCon - New York 2017](https://qconnewyork.com/ny2017/users/vinicius-gomes)

# Tech Stack

- Java
- Spring (Boot, Data)
- [Axon](http://axonframework.org)
- [RabbitMQ](http://rabbitmq.com)

# Running

Ensure you have a recent version of Java, Maven, and RabbitMQ.

```
$ cd menu
$ mvn clean install
$ mvn -Dserver.port=8080 spring-boot:run &
$ cd ../customers
$ mvn clean install
$ mvn -Dserver.port=8081 spring-boot:run &
$ cd ../orders
$ mvn clean install
$ mvn -Dserver.port=8082 spring-boot:run &
```

