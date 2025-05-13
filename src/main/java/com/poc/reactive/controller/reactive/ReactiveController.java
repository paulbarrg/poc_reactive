package com.poc.reactive.controller.reactive;

import java.time.Duration;
import java.util.List;

import com.poc.reactive.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive")
public class ReactiveController {

	private static final Logger log = LoggerFactory.getLogger(ReactiveController.class);
	private final List<User> users = List.of(
			new User("1", "Alice"),
			new User("2", "Bob"),
			new User("3", "Charlie"),
			new User("4", "David"),
			new User("5", "Daniel")
	);

	@GetMapping("/hello/{prefix}/{delay}")
	public Flux<User> reactiveHello(@PathVariable String prefix,@PathVariable int delay) {
		log.info("Reactive request received with delay: {}ms. Thread: {}", delay, Thread.currentThread().getName());

		// Simulate non-blocking delay
		return Flux.fromIterable(users)
				.filter(user -> user.name().toLowerCase().startsWith(prefix.toLowerCase()))
				.defaultIfEmpty(new User("1","no se encontraron usuarios")
				).delaySequence(Duration.ofMillis(delay))
				.doOnSubscribe(subscription -> log.info("Reactive subscription started for delay: {}ms. Thread: {}", delay,
				Thread.currentThread().getName()))
				.doOnError(error -> log.error("Reactive error for delay: {}ms", delay, error));
	}

}
