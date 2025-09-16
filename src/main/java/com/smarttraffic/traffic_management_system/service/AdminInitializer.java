package com.smarttraffic.traffic_management_system.service;

import com.smarttraffic.traffic_management_system.entity.User;
import com.smarttraffic.traffic_management_system.repository.UserRepository;
import com.smarttraffic.traffic_management_system.service.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Configuration
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if an admin already exists
        boolean adminExists = userRepository.findAll()
                .stream()
                .anyMatch(user -> user.getRole() == Role.ROLE_ADMIN);

        if (!adminExists) {
            User admin = new User();
            admin.setId(UUID.randomUUID());
            admin.setName("Super Admin");
            admin.setBadgeId("ADMIN001");
            admin.setEmail("admin@traffic.com");
            admin.setPhone("9999999999");
            admin.setJurisdictionArea("All Areas");
            admin.setPassword(passwordEncoder.encode("Admin@123")); // choose a strong password
            admin.setRole(Role.ROLE_ADMIN);
            admin.setApproved(true); // admin is always approved

            userRepository.save(admin);

            System.out.println("Default admin created: BadgeId=ADMIN001, Password=Admin@123");
        } else {
            System.out.println("Admin already exists, skipping creation.");
        }
    }
}
