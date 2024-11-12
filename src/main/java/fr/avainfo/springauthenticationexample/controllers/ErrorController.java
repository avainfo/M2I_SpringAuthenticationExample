package fr.avainfo.springauthenticationexample.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api/v1")
public class ErrorController {
	@GetMapping("/error")
	public String errorMessage() {
		return "accessDenied";
	}
}