package fr.avainfo.springauthenticationexample.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private")
public class PrivateController {
	@GetMapping("/admin")
	public String adminEndpoint() {
		return "Vous êtes administrateur !";
	}
	@GetMapping("/user")
	public String userEndpoint() {
		return "Vous êtes un utilisateur privé !";
	}
}