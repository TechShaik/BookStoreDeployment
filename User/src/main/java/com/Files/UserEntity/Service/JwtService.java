package com.Files.UserEntity.Service;

import java.io.Console;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.Files.UserEntity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET_KEY = "f30b9aa316baa495e796b7af9669bcdb65f3d8a6531005a4ec3acd4259eb1c6c1bf2752840a326ec5310648e9727c4dcfbf19bf7a10aa2d578dfa8ede511c64fc87713327c76f76b423d57350aa0672611f5f5c38e34252a0393f407d547b83e820b5c11b5597d0e10766ec6b4b8aee1c317ee588028d6457ba9971be3da88f68d6df19c78e0a88d0fb331ebd6d5b291ec2dc25f7def552bf23d5e3b5139dede2c5440b8d89c2bdbf0dd10b246aae6b3733c59704a4775a2c7865539eb5aaca47488d87fa748dee2b878fb58b0011c513b8861b482f1d79a2854a7e987c77a95d85abc5d23d0d40113442b1399e80e0fbc108b8f0a26a873f9736c95fac03fd1==";  
 
	public Jws<Claims> validateToken(String token){
		Jws<Claims> claimsJws=Jwts.parser()
				.setSigningKey(generateKey()).build().parseClaimsJws(token);
		return claimsJws;
	}
	 
    private SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts
        		.builder()
        		.setClaims(claims)
        		.setSubject(username)
        		.setIssuer(username)
        		.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact(); 
        		 
    }

    
	public String extractUsername(String token) {
		     
		return extractClaims(token,Claims::getSubject);
	}
	
	
	
	private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
 		 Claims claims=extractClaims(token);
 		 return claimResolver.apply(claims);
	}

	private Claims extractClaims(String token) {
		return Jwts
		.parser()
		.verifyWith(generateKey())
		.build()
		.parseSignedClaims(token)
		.getPayload();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
	final String userName=extractUsername(token);
		return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
 		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		 return extractClaims(token,Claims::getExpiration);
	}

    
     
}
