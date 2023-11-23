package com.device.shop.controller;

import com.device.shop.security.JwtUtils;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(
            @RequestParam @Parameter(
                    description = "User's username",
                    required = true,
                    content = @Content(schema = @Schema(type = "string"))
            ) String username,
            @RequestParam @Parameter(
                    description = "User's password",
                    required = true,
                    content = @Content(schema = @Schema(type = "string", format = "password"))
            ) String password) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity
                .ok(jwt);
    }

}
