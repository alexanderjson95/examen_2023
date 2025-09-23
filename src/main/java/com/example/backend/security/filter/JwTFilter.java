package com.example.backend.security.filter;

import com.example.backend.Exceptions.JwTAuthenticationException;
import com.example.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwTFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtS;
    @Autowired
    UserDetailsService uS;

    public UserDetails getUserdetails(String token){
        String username = jwtS.extractUsername(token);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (username == null){
            throw new JwTAuthenticationException(HttpServletResponse.SC_UNAUTHORIZED, "Not authenticated");
        }
        return uS.loadUserByUsername(username);
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull  HttpServletResponse response,@NonNull FilterChain filterChain) throws IOException {
        try{
            final String authHeader = request.getHeader("Authorization");


            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            final String token = authHeader.substring(7);
            UserDetails details = getUserdetails(token);

            if (jwtS.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                details,
                                null,
                                details.getAuthorities()
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request,response);
        }
        catch (Exception e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token!");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/users/register") || path.startsWith("/users/roles") || path.startsWith("/authenticate") || path.startsWith("/refresh");
    }
}
