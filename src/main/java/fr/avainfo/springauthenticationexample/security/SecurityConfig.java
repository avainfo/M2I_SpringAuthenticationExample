package fr.avainfo.springauthenticationexample.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private DataSource dataSource;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/api/public/**").permitAll()
						.requestMatchers("/api/private/admin").hasRole("ADMIN")
						.requestMatchers("/api/private/user").hasRole("UPPER_USER")
						.anyRequest().authenticated()
				)
				.exceptionHandling(exception -> exception.accessDeniedPage("/api/v1/error"))
				.formLogin(form -> form.defaultSuccessUrl("/api/private"));
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager(dataSource);
		userDetailsService.setUsersByUsernameQuery("SELECT username, password, true FROM users WHERE username = ?");
		userDetailsService.setAuthoritiesByUsernameQuery("SELECT username, role FROM roles WHERE username = ?");
		return userDetailsService;
	}
}