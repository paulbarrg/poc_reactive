package com.poc.reactive.controller.blocking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blocking")
public class BlockingController {

	private static final Logger log = LoggerFactory.getLogger(BlockingController.class);

	@GetMapping("/hello/{delay}")
	public String blockingHello(@PathVariable int delay) {
		log.info("Blocking request received with delay: {}ms. Thread: {}", delay, Thread.currentThread().getName());
		try {
			// Simulate blocking I/O (e.g., slow DB call, external API)
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.error("Blocking thread interrupted", e);
			return "Error: Thread interrupted";
		}
		String response = "Hello Blocking! Delayed for " + delay + "ms. Thread: " + Thread.currentThread().getName();
		log.info("Blocking request completed for delay: {}ms", delay);
		return response;
	}

}
