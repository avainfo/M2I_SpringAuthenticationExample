package fr.avainfo.springauthenticationexample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class DBUserDetailsServices  implements UserDetailsService {
	@Autowired
	private DataSource dataSource;

	@Override
	public UserDetails loadUserByUsername(String username) {
		JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager(dataSource);
		userDetailsService.setUsersByUsernameQuery("SELECT username, password, true FROM users WHERE username = ?");
		userDetailsService.setAuthoritiesByUsernameQuery("SELECT username, role FROM roles WHERE username = ?");
		return userDetailsService.loadUserByUsername(username);
	}
}
