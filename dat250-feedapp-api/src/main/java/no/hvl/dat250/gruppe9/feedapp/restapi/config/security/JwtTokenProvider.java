package no.hvl.dat250.gruppe9.feedapp.restapi.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;


@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.expireTTL}")
    private int expireTTL;

    public String generateToken(Authentication auth) {
        var now = new Date();
        var exp = new Date(now.getTime()+ expireTTL);

        if(auth.getPrincipal().getClass().equals(AccountPrincipals.class)) {
            var accountPrincipals = (AccountPrincipals) auth.getPrincipal();
            return Jwts.builder()
                    .setSubject(accountPrincipals.getId())
                    .setIssuedAt(new Date())
                    .setExpiration(exp)
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
        }
        var accountPrincipals = (DevicePrincipals) auth.getPrincipal();
        return Jwts.builder()
                .setSubject(accountPrincipals.getId())
                .setIssuedAt(new Date())
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();


    }


    public String getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Optional<String> parseHeader(String token) {
        String result = "";
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            result = token.substring(7, token.length());
        }
        if (!result.isEmpty())
            return Optional.ofNullable(getUserIdFromJwt(result));

        return Optional.empty();
    }


    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty: {}", ex.getMessage());
        }
        return false;
    }
}
