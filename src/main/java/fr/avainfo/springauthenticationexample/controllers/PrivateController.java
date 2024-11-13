package fr.avainfo.springauthenticationexample.controllers;

import fr.avainfo.springauthenticationexample.services.RoleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private")
public class PrivateController {

	@Autowired
	private RoleServices roleServices;

	@GetMapping("/admin")
	public String adminEndpoint() {
		return roleServices.adminAction();
	}

	@GetMapping("/user")
	public String userEndpoint() {
		return roleServices.userAction();
	}
}