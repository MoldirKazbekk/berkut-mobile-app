package kz.sdu.edu.berkutapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kz.sdu.edu.berkutapp.model.AppUser;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "secret-key-berkut";

    private final String PHONE = "phoneNumber";

    private final String ROLE = "role";

    private final int TOKEN_TTL = 1000 * 60 * 60 * 300; //300 hours jwt ttl

    public String extractId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractPhoneNumber(String token) {
        return extractClaim(token, (claims) -> claims.get(PHONE, String.class));
    }

    public String extractRole(String token) {
        return extractClaim(token, (claims) -> claims.get(ROLE, String.class));
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
        return createToken(appUser);
    }

    private String createToken(AppUser appUser) {
        return Jwts.builder()
                .setSubject(appUser.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_TTL))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .claim(PHONE, appUser.getPhoneNumber())
                .claim(ROLE, appUser.getRole())
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String actualUsername = extractId(token);
        return (username.equals(actualUsername) && !isTokenExpired(token));
    }
}
