package com.smarttraffic.traffic_management_system.controller;

import com.smarttraffic.traffic_management_system.dto.ApiResponse;
import com.smarttraffic.traffic_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @PutMapping("/approve/{id}")
     public ResponseEntity<ApiResponse<Map<String,UUID>>> approveUser(@PathVariable UUID id){
       return userRepository.findById(id)
                .map(user -> {
                    user.setApproved(true);
                    userRepository.save(user);

                    Map<String,UUID> responseData=Map.of("userId",user.getId());
                    ApiResponse<Map<String,UUID>> response=
                            new ApiResponse<>("success","User approved successfully",responseData);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(()->ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("error",
                        "user not found",null)));

    }
}
