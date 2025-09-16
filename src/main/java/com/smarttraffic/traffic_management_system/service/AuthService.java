package com.smarttraffic.traffic_management_system.service;


import com.smarttraffic.traffic_management_system.entity.PasswordResetToken;
import com.smarttraffic.traffic_management_system.entity.User;
import com.smarttraffic.traffic_management_system.repository.PasswordResetTokenRepository;
import com.smarttraffic.traffic_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

   public String generateResetToken(String emailOrBadgeId){
       User user=userRepository.findByBadgeIdOrEmail(emailOrBadgeId,emailOrBadgeId)
               .orElseThrow(()->new UsernameNotFoundException("User not found"));


       String token= UUID.randomUUID().toString();

       PasswordResetToken passwordResetToken=new PasswordResetToken();
       passwordResetToken.setToken(token);
       passwordResetToken.setUser(user);
       passwordResetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
       passwordResetToken.setUsed(false);

       passwordResetTokenRepository.save(passwordResetToken);

       return token;
   }


   public void resetPassword(String token,String newPassword){
       PasswordResetToken resetToken=passwordResetTokenRepository.findByToken(token)
               .orElseThrow(()-> new RuntimeException("Invalid reset token"));

       if(resetToken.isUsed()||resetToken.getExpiryDate().isBefore(LocalDateTime.now())){
           throw new RuntimeException("Token expired or already used");
       }

       User user=resetToken.getUser();
       user.setPassword(passwordEncoder.encode(newPassword));
       userRepository.save(user);

       resetToken.setUsed(true);
       passwordResetTokenRepository.save(resetToken);
   }
}
