package com.sujan.jobportal.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY=
            "c4a833d1258a23ace82d35bc0f5901f582b8fab4be9b33d75b85f081370d78ad";

    public String extractUserName(String jwt) {
        return extractClaim(jwt,Claims::getSubject);
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        String username=extractUserName(jwt);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    private boolean isTokenExpired(String token) {
         return extractExpirationDate(token).equals(new Date());
    }

    private Date extractExpirationDate(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimResolver){
           Claims claims= extractAllClaims(token);
           return  claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey(){
      byte[] keyBytes= Decoders.BASE64URL.decode(SECRET_KEY);
      return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateToken(UserDetails user,String role) {
        return generateToken(new HashMap<>(),user,role);
    }

    public String generateToken(
            Map<String,Object> extraClaims,
            UserDetails userDetails
            ,String role
    ){
        extraClaims.put("role",role);
          return Jwts
                  .builder()
                  .setClaims(extraClaims)
                  .setSubject(userDetails.getUsername())
                  .setIssuedAt(new Date(System.currentTimeMillis()))
                  .setExpiration(new Date(System.currentTimeMillis()+(60*60*24*1000)))
                  .signWith(getSigningKey())
                  .compact();
    }
}
