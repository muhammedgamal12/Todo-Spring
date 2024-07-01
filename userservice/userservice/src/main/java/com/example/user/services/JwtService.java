package com.example.user.services;



import com.example.user.model.UserModel;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {


    TokenService tokenService;
    private final String secret_key = "mysecretkey";

    protected final long accessTokenValidity = 2000*60*1000;

    private final JwtParser jwtParser;

    public JwtService()
    {

        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }

    public String createToken(UserModel user , Map<String , Object> extraClaims) {


        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getEmail())

                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
    }

    private Claims parseJwtClaims(String token)
    {
        return jwtParser.parseClaimsJws(token).getBody();
    }


    //retrieve Token
    public String getToken(HttpServletRequest request)
    {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring("Bearer".length());
        }
        return null;
    }

    public Claims getClaims(HttpServletRequest req)
    {
        try {
            String token = getToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }


    public boolean isTokenExpired(Date expirationDate) throws AuthenticationException
    {
        try {
            if(expirationDate.before(new Date()))
                return true;
            else
                return false;
        } catch (Exception e) {

            throw e;
        }
    }


    public boolean isTokenValid(String accessToken , UserDetails userDetails) {
        String username = userDetails.getUsername();
        Claims claims = parseJwtClaims(accessToken);
        return username.equals(claims.getSubject()) && !isTokenExpired(claims.getExpiration());
    }

    private List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }

}
