package in.nareshit.raghu.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

@Component
public class JWTUtil {
	
	@Value("${app.secret}")
	private String secret;
	
	public boolean validatetoken(String token,String username) {
		String tokenUserName= getUsername(token);
		
		return (username.equals(tokenUserName)  &&  !IsTokenExpired(token));
	}
	
	public boolean IsTokenExpired(String token) {
		Date expDate=getExpDate(token);
		return expDate.before(new Date(System.currentTimeMillis()));
	}
	
	public String getUsername(String token) {
		return getclaim(token).getSubject();
	}
	
	public Date getExpDate(String token) {
		return  getclaim(token).getExpiration();
	}
	
	public Claims getclaim(String token) {
		return Jwts.parser()
				.setSigningKey(secret.getBytes())
		        .parseClaimsJws(token)
		        .getBody();
	}
		
	
	public String generateToken(String subject) {
		return Jwts.builder()
			   .setSubject(subject)
			   .setIssuer("NareshIT")
			   .setIssuedAt(new Date(System.currentTimeMillis()))
			   .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
			   .signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
		
	}
	

}
