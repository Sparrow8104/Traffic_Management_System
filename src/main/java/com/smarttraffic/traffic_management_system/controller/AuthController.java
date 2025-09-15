package com.smarttraffic.traffic_management_system.controller;

import com.smarttraffic.traffic_management_system.dto.AuthRequestDto;
import com.smarttraffic.traffic_management_system.dto.AuthResponseDto;
import com.smarttraffic.traffic_management_system.entity.User;
import com.smarttraffic.traffic_management_system.security.JwtUtils;
import com.smarttraffic.traffic_management_system.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequestDto authRequestDto){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getLogin(),authRequestDto.getPassword()));
            User user=(User)customUserDetailsService.loadUserByUsername(authRequestDto.getLogin());
            String token=jwtUtils.generateToken(user.getBadgeId());

            return ResponseEntity.ok(new AuthResponseDto(token));
        }
        catch(AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
 }

}
