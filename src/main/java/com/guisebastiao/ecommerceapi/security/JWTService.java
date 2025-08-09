package com.guisebastiao.ecommerceapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String secretToken;

    @Value("${access.token.duration}")
    private int accessTokenDuration;

    @Value("${refresh.token.duration}")
    private int refreshTokenDuration;

    private final String issuer = "ecommerce-api";

    public String generateAccessToken(UserDetails userDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretToken);
            Date expires = Date.from(Instant.now().plus(this.accessTokenDuration,  ChronoUnit.SECONDS));

            return JWT.create()
                    .withSubject(userDetails.getUsername())
                    .withIssuedAt(new Date())
                    .withExpiresAt(expires)
                    .withIssuer(issuer)
                    .sign(algorithm);
        } catch (Exception exception) {
            throw new RuntimeException("Algo deu errado, tente novamente mais tarde");
        }
    }

    public String generateRefreshToken(UserDetails userDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretToken);
            Date expires = Date.from(Instant.now().plus(this.refreshTokenDuration, ChronoUnit.SECONDS));

            return JWT.create()
                    .withSubject(userDetails.getUsername())
                    .withIssuedAt(new Date())
                    .withExpiresAt(expires)
                    .withIssuer(issuer)
                    .sign(algorithm);
        } catch (Exception exception) {
            throw new RuntimeException("Algo deu errado, tente novamente mais tarde");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretToken);

            return JWT.require(algorithm)
                    .withIssuer(this.issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception exception) {
            return null;
        }
    }

    public String extractUsername(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getSubject();

    }

    public boolean isExpired(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        Date expiration = decodedJWT.getExpiresAt();
        return expiration.before(new Date());
    }
}
