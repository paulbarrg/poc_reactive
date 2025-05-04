package com.poc.reactive.controller.reactive;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive")
public class ReactiveController {

	private static final Logger log = LoggerFactory.getLogger(ReactiveController.class);

	@GetMapping("/hello/{delay}")
	public Mono<String> reactiveHello(@PathVariable int delay) {
		log.info("Reactive request received with delay: {}ms. Thread: {}", delay, Thread.currentThread().getName());

		// Simulate non-blocking delay
		return Mono.delay(Duration.ofMillis(delay)).map(tick -> {
			// This map operation likely runs on a different thread (parallel scheduler)
			// after the delay
			String response = "Hello Reactive! Delayed for " + delay + "ms. Thread: "
					+ Thread.currentThread().getName();
			log.info("Reactive processing after delay for: {}ms. Thread: {}", delay, Thread.currentThread().getName());
			return response;
		}).doOnSubscribe(subscription -> log.info("Reactive subscription started for delay: {}ms. Thread: {}", delay,
				Thread.currentThread().getName()))
				.doOnError(error -> log.error("Reactive error for delay: {}ms", delay, error));
	}

}
