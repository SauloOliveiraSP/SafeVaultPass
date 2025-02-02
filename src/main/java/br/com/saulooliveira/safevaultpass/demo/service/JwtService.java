package br.com.saulooliveira.safevaultpass.demo.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.saulooliveira.safevaultpass.demo.entity.UsersEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long expiration;

    // Método para gerar o token, incluindo o userId
    public String generateToken(UsersEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());  // Inclui o ID do usuário
        claims.put("username", user.getUsername());
        claims.put("roles", user.getAuthorities());
        return createToken(claims, user.getUsername());
    }

    // Método para validar o token
    public Boolean validateToken(String token, UserDetails userDetails) {
        Date expirationDate = extractExpiration(token);
        if (expirationDate.before(new Date())) {
            return false;
        }
        String username = extractUsername(token);
        return userDetails.getUsername().equals(username) && !expirationDate.before(new Date());
    }

    // Método privado para criar o token
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Método para extrair o username do token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Método para extrair a data de expiração do token
    private Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    // Método para extrair o userId do token
    public Long extractUserId(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
    
            // Extraímos o "id" do token, que foi configurado no método generateToken
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("id", Long.class);    
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid or expired token", e);
        }
    }

    // Método para gerar a chave secreta
    private Key getSignKey() {
        if (secretKey.length() < 32) {
            // Se a chave fornecida no arquivo de configuração for muito pequena, gera uma chave segura
            return Keys.secretKeyFor(SignatureAlgorithm.HS256);
        }
        // Se a chave for suficientemente grande (256 bits), use-a
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
