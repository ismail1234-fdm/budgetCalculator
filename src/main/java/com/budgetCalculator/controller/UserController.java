package com.budgetCalculator.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.budgetCalculator.entity.AppUser;
import com.budgetCalculator.entity.Role;
import com.budgetCalculator.requests.RequestForRoleToUserForm;
import com.budgetCalculator.response.JwtErrorResponse;
import com.budgetCalculator.response.JwtResponse;
import com.budgetCalculator.response.UserRequestResponse;
import com.budgetCalculator.services.UserServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserServiceImp userServiceImp;

    @GetMapping("users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(this.userServiceImp.getUsers());
    }

    @PostMapping("users/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser appUser) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(this.userServiceImp.saveUser(appUser));
    }

    @PostMapping("role/save")
    public ResponseEntity<Role> saveUser(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(this.userServiceImp.saveRole(role));
    }

    @PostMapping("role/addToUser")
    public ResponseEntity<?> saveUser(@RequestBody RequestForRoleToUserForm requestForRoleToUserForm) throws JsonProcessingException {
        this.userServiceImp.addRoleToUser(requestForRoleToUserForm.getUsername(), requestForRoleToUserForm.getRolename());
        return ResponseEntity.ok().build();
    }

    @GetMapping("users/getUserByUsername")
    public UserRequestResponse getUserbyUsername(@RequestParam String username) {
        System.out.println("response:" + this.userServiceImp.getUserByUsername(username));
       return this.userServiceImp.getUserByUsername(username);
    }

    //if the Token is expired, we send to client thé refreshToken
    @GetMapping("token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        final Gson gson = new Gson();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                AppUser user = userServiceImp.getUser(username);

                String refresh_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10* 60 *1000))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles",user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                JwtResponse jwtResponse = new JwtResponse();
                jwtResponse.setRefresh_token(refresh_token);
                jwtResponse.setUsername(user.getUsername());
                String jwtResponseToJsonString = gson.toJson(jwtResponse);

                PrintWriter out = response.getWriter();
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("UTF-8");
                out.println(jwtResponseToJsonString);
                out.flush();

            } catch (Exception exception) {
                JwtErrorResponse jwtErrorResponse = new JwtErrorResponse();
                jwtErrorResponse.setErrorMessage(exception.getMessage());
                jwtErrorResponse.setHttpStatus(FORBIDDEN);
                String jwtResponseToJsonString = gson.toJson(jwtErrorResponse);

                PrintWriter out = response.getWriter();
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("UTF-8");
                out.println(jwtResponseToJsonString);
                out.flush();

            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
