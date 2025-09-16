package com.smarttraffic.traffic_management_system.controller;

import com.smarttraffic.traffic_management_system.dto.*;
import com.smarttraffic.traffic_management_system.entity.User;
import com.smarttraffic.traffic_management_system.security.JwtUtils;
import com.smarttraffic.traffic_management_system.service.AuthService;
import com.smarttraffic.traffic_management_system.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequestDto authRequestDto){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getLogin(),authRequestDto.getPassword()));
            User user=(User)customUserDetailsService.loadUserByUsername(authRequestDto.getLogin());
            if(!user.isApproved())
            {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse<>("error","Account not approved by admin yet",null));
            }
            String token=jwtUtils.generateToken(user.getBadgeId());

            return ResponseEntity.ok(new AuthResponseDto(token));
        }
        catch(AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
 }

    @PostMapping("/forget_password")
     public ResponseEntity<?> forgotPassword(@RequestBody ForgetPasswordRequest request){
         String token=authService.generateResetToken(request.getEmailOrBadgeId());

         return ResponseEntity.ok(Map.of("ResetToken",token));
     }


    @PostMapping("/reset_password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request){
       authService.resetPassword(request.getToken(),request.getNewPassword());
       return ResponseEntity.ok(Map.of("message","Password reset successful"));
    }


}