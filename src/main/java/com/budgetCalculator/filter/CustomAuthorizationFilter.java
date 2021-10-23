package com.budgetCalculator.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.budgetCalculator.response.JwtErrorResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private Gson gson = new Gson();

    // for every Request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("api/login") || request.getServletPath().equals("/api/token/refresh")) {
            filterChain.doFilter(request, response);
        } else {

            // if token was send already in Client, and Client tries to access endpoints, in which Authorization is required
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSignature();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null,authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                    //if token is invalid or explored time
                } catch (Exception exception) {
                    JwtErrorResponse jwtErrorResponse = new JwtErrorResponse();
                    jwtErrorResponse.setErrorMessage(exception.getMessage());
                    jwtErrorResponse.setHttpStatus(FORBIDDEN);
                    log.error("Error loggimg in : {}", exception.getMessage());
                    String jwtResponseToJsonString = this.gson.toJson(jwtErrorResponse);

                    PrintWriter out = response.getWriter();
                    response.setContentType(APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding("UTF-8");
                    out.println(jwtResponseToJsonString);
                    out.flush();

                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
