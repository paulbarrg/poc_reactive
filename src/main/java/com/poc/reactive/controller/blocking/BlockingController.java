package com.poc.reactive.controller.blocking;

import com.poc.reactive.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blocking")
public class BlockingController {

	private static final Logger log = LoggerFactory.getLogger(BlockingController.class);
	private final List<User> users = List.of(
			new User("1", "Alice"),
			new User("2", "Bob"),
			new User("3", "Charlie"),
			new User("4", "David"),
			new User("5", "Daniel")
	);
	@GetMapping("/hello/{prefix}/{delay}")
	public List<User> blockingHello(@PathVariable String prefix, @PathVariable int delay) {
		log.info("Blocking request received with delay: {}ms. Thread: {}", delay, Thread.currentThread().getName());
		try {
			// Simulate blocking I/O (e.g., slow DB call, external API)
			Thread.sleep(delay);
			List<User> userList = users.stream()
					.filter(user -> user.name().toLowerCase().startsWith(prefix.toLowerCase()))
					.collect(Collectors.toList());
			if(userList.isEmpty()){
				userList =List.of(
						new User("1", "no se encontraron usuarios"));
			}
			return userList;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.error("Blocking thread interrupted", e);
			return new ArrayList<>();
		}
	}

}
