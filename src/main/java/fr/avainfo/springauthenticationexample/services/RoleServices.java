package fr.avainfo.springauthenticationexample.services;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class RoleServices {
	@Secured("ROLE_ADMIN")
	public String adminAction() {
		return "Vous êtes administrateur !";
	}

	@PreAuthorize("hasRole('UPPER_USER') or hasAuthority('') or principal.username == 'Mega SuperAdmin'")
	public String userAction() {
		return "Vous êtes un utilisateur privé ou un Mega Super Admin !";
	}
}
