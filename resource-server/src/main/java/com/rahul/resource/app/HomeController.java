package com.rahul.resource.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/api/protect-resource")
	public String welcome() {
		return "welcome";
	}
}
