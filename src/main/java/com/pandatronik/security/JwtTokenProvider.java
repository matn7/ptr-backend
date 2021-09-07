package com.pandatronik.security;

import com.pandatronik.backend.persistence.domain.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.DefaultClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.pandatronik.security.SecurityConstants.EXPIRATION_TIME;
import static com.pandatronik.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider implements Serializable {

    private static final long serialVersionUID = -3019870784923593359L;

    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenProvider.class);

    private Clock clock = DefaultClock.INSTANCE;

    private String secret = SecurityConstants.SECRET;

    private Long expiration = SecurityConstants.EXPIRATION_TIME;

    // Generate the token
    public String generateToken(Authentication authentication) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String, Object> claims = new HashMap<>();

        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getLastName());

        return Jwts.builder().setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    // Validate the token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            LOG.error("Invalid JWT Signature");
        } catch (MalformedJwtException ex) {
            LOG.error("Invalid JWT Token");
        } catch (ExpiredJwtException ex) {
            LOG.error("Expired JWT Token");
        } catch (UnsupportedJwtException ex) {
            LOG.error("Unsupported JWT Exception");
        } catch (IllegalArgumentException ex) {
            LOG.error("JWT claims string is empty");
        }
        return false;
    }

    // Get user Id from token
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = (String) claims.get("id");

        return Long.parseLong(id);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        UserEntity user = (UserEntity) userDetails;
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }

}
