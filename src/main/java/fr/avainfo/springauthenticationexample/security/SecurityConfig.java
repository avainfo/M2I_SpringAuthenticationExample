package fr.avainfo.springauthenticationexample.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/api/public/**")
						.permitAll()
						.anyRequest()
						.authenticated()
				)
				.formLogin(form -> form.defaultSuccessUrl("/api/private"))
				.httpBasic(withDefaults());
		return http.build();
	}
	@Bean
	public UserDetailsService userDetailsService() {
		var user = User.withUsername("user")
				.password("{noop}password")
				.roles("USER")
				.build();
		var admin = User.withUsername("admin")
				.password("{noop}admin123")
				.roles("ADMIN")
				.build();
		return new InMemoryUserDetailsManager(user, admin);
	}
}