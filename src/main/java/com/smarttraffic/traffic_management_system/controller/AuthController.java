package com.smarttraffic.traffic_management_system.controller;

import com.smarttraffic.traffic_management_system.dto.AuthRequestDto;
import com.smarttraffic.traffic_management_system.dto.AuthResponseDto;
import com.smarttraffic.traffic_management_system.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;
    public ResponseEntity<?> generateToken(@RequestBody AuthRequestDto authRequestDto){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(),authRequestDto.getPassword()));
            String token=jwtUtils.generateToken(authRequestDto.getUsername());

            return ResponseEntity.ok(new AuthResponseDto(token));
        }
        catch(AuthenticationException e){
            return ResponseEntity.ok(new AuthResponseDto(e.getMessage()));
        }
 }

}
