package com.smarttraffic.traffic_management_system.controller;

import com.smarttraffic.traffic_management_system.dto.ApiResponse;
import com.smarttraffic.traffic_management_system.dto.RegisterRequestDto;
import com.smarttraffic.traffic_management_system.entity.User;
import com.smarttraffic.traffic_management_system.repository.UserRepository;
import com.smarttraffic.traffic_management_system.service.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class RegistrationController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<ApiResponse<Map<String, UUID>>> register(@RequestBody RegisterRequestDto dto){
       if(userRepository.findByBadgeIdOrEmail(dto.getBadgeId(), dto.getEmail()).isPresent()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                   new ApiResponse<>("error","User with same email or badgeId already exists",null)
           );}
           User user=new User();
           user.setName(dto.getName());
           user.setBadgeId(dto.getBadgeId());
           user.setEmail(dto.getEmail());
           user.setJurisdictionArea(dto.getJurisdictionArea());
           user.setPhone(dto.getPhone());
           user.setApproved(false);
           user.setRole(Role.ROLE_OFFICER);
           user.setPassword(passwordEncoder.encode(dto.getPassword()));

           userRepository.save(user);
           Map<String,UUID> responseData=Map.of("userId",user.getId());
           return ResponseEntity.ok(new ApiResponse<>("success","User registered successfully! Waiting for admin " +
                   "approval",responseData));
       }
    }


