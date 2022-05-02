package com.stpr.piupitter.service;


import com.stpr.piupitter.data.model.user.AppUser;
import com.stpr.piupitter.data.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

import static com.stpr.piupitter.data.model.user.Role.USER;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Value("${confirmLink.path}")
    private String confirmLink;

    private final UserRepo userRepo;
    private final MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findAppUserByUsername(username);
    }

    public boolean addUser(AppUser user){

        if(userRepo.findAppUserByUsername(user.getUsername()) != null){
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);

        if(!user.getEmail().isEmpty()){
            String welcomeMessage = String.format(
                    "Hello, %s! \n" +
                            "Welcome to piupitter. Please, activate your profile: " + confirmLink + "/activate/%s",
                                user.getUsername(), user.getActivationCode());
            mailSender.send(user.getEmail(), "Activation code", welcomeMessage);
        }
        return true;
    }

    public boolean activateUser(String code) {

        AppUser user = userRepo.findByActivationCode(code);
        if(user == null){
            return false;
        }

        user.setActivationCode(null);
        userRepo.save(user);
        return true;
    }
}
