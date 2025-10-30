package com.example.MovieBookingApplication.jwt;

import com.example.MovieBookingApplication.Repositories.UserRepository;
import com.example.MovieBookingApplication.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.webauthn.authentication.WebAuthnAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Security;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;

        if (requestHeader == null || !requestHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = requestHeader.substring(7); // This to get Jwt only, not with Bearer
        username = jwtService.extractUsername(jwtToken);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            var userDetails = userRepository.findUserByUsername(username)
                    .orElseThrow(()-> new RuntimeException("There is no user found"));

            if(jwtService.isJwtTokenValid(jwtToken, userDetails)){
                List<SimpleGrantedAuthority> roles = userDetails
                        .getRoles()
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        username, null, roles
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
