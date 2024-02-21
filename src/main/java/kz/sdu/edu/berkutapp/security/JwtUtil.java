package kz.sdu.edu.berkutapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kz.sdu.edu.berkutapp.model.AppUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "secret-key-berkut";

    public String extractId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractPhoneNumber(String token) {
        return extractClaim(token, (claims) -> claims.get("phoneNumber", String.class));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(AppUser appUser) {
        return createToken(appUser.getId(), appUser.getPhoneNumber());
    }

    private String createToken(Long id, String phoneNumber) {
        return Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60)) // 1 minutes
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .claim("phoneNumber", phoneNumber)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String actualUsername = extractId(token);
        return (username.equals(actualUsername) && !isTokenExpired(token));
    }
}
