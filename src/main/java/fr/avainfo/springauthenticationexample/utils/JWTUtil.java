package fr.avainfo.springauthenticationexample.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {
	@Value("${security.jwt.secret-key}")
	private String SECRET_KEY;

	@Value("${security.jwt.expiration-time}")
	private long jwtExpiration;

	public String generateToken(String username) {
		return Jwts.builder()
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + jwtExpiration)) // 10 hours
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
				.compact();
	}

	public String extractUsername(String token) {
		return Jwts.parser()
				.verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}

	public boolean isTokenValid(String token, String username) {
		String extractedUsername = extractUsername(token);
		return (username.equals(extractedUsername) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return Jwts.parser()
				.verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getExpiration().before(new Date());
	}
}