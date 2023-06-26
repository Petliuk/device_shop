package com.device.shop.security;

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
    public ResponseEntity<String> authenticateUser(@RequestParam MultiValueMap body) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(String.valueOf(body.get("username"))
                        ,String.valueOf(body.get("password"))));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity
                .ok(jwt);
    }

}
