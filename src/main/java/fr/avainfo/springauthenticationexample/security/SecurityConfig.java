package fr.avainfo.springauthenticationexample.security;

import fr.avainfo.springauthenticationexample.handlers.RoleAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/api/public/**").permitAll()
						.requestMatchers("/api/private/admin").hasRole("ADMIN")
						.requestMatchers("/api/private/user").hasRole("UPPER_USER")
						.anyRequest().authenticated()
				)
				.formLogin(form -> form.defaultSuccessUrl("/api/private"))
				.exceptionHandling(exception -> exception
						.accessDeniedHandler(new RoleAccessDeniedHandler())
				);
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		var user = User.withUsername("user")
				.password("{noop}password")
				.roles("USER")
				.build();
		var superUser = User.withUsername("SuperUser")
				.password("{noop}password")
				.roles("UPPER_USER")
				.build();
		var admin = User.withUsername("admin")
				.password("{noop}admin123")
				.roles("ADMIN")
				.build();
		return new InMemoryUserDetailsManager(user, superUser, admin);
	}
}