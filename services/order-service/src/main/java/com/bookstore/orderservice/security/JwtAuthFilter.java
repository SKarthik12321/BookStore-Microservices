package com.bookstore.orderservice.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Value("${jwt.secret:bookstore-super-secret-key-for-jwt-authentication-2024}") private String secret;
    @Override
    protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain) throws ServletException,IOException {
        String header=req.getHeader("Authorization");
        if(header!=null&&header.startsWith("Bearer ")){
            try{
                var claims=Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parseSignedClaims(header.substring(7)).getPayload();
                String username=claims.getSubject(); String role=claims.get("role",String.class);
                if(username!=null&&SecurityContextHolder.getContext().getAuthentication()==null)
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username,null,List.of(new SimpleGrantedAuthority("ROLE_"+role))));
            }catch(Exception ignored){}
        }
        chain.doFilter(req,res);
    }
}