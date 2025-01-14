package com.sujan.jobportal.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull  HttpServletResponse response,
           @NonNull  FilterChain filterChain
    ) throws ServletException,
            IOException,
            UsernameNotFoundException {
        System.out.println(request);
        String header= request.getHeader("Authorization");
        System.out.println("header"+header);
        String username;
        String jwt;

        if(header==null || !header.startsWith("Bearer ")){
           filterChain.doFilter(request,response);
           return;
        }

        jwt=header.substring(7);
        username=jwtService.extractUserName(jwt);
        System.out.println("username--"+username);
        if(
                username!=null &&
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()==null){


            UserDetails userDetails=
                    this.userDetailsService.loadUserByUsername(username);
if(jwtService.isTokenValid(jwt,userDetails)){

  UsernamePasswordAuthenticationToken token= new  UsernamePasswordAuthenticationToken
          (
           userDetails
           ,null,
           userDetails.getAuthorities()
           );
  token
          .setDetails(new WebAuthenticationDetailsSource().buildDetails(request))
          ;
  SecurityContextHolder.getContext().setAuthentication(token);

}

}
        filterChain.doFilter(request,response);
        }
    }

