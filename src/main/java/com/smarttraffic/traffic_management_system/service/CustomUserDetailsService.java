package com.smarttraffic.traffic_management_system.service;

import com.smarttraffic.traffic_management_system.entity.User;
import com.smarttraffic.traffic_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user= userRepository.findByBadgeIdOrEmail(login,login)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));

        return user;
    }


}
