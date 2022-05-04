package com.stpr.piupitter.service;


import com.stpr.piupitter.data.model.user.AppUser;
import com.stpr.piupitter.data.model.user.Role;
import com.stpr.piupitter.data.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.stpr.piupitter.data.model.user.Role.USER;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Value("${confirmLink.path}")
    private String confirmLink;

    private final UserRepo userRepo;
    private final MailSender mailSender;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findAppUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public boolean addUser(AppUser user){

        if(userRepo.findAppUserByUsername(user.getUsername()) != null){
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        sendMessage(user);
        return true;
    }

    private void sendMessage(AppUser user) {
        if(!user.getEmail().isEmpty()){
            String welcomeMessage = String.format(
                    "Hello, %s! \n" +
                            "Welcome to piupitter. Please, activate your profile: " + confirmLink + "/activate/%s",
                                user.getUsername(), user.getActivationCode());
            mailSender.send(user.getEmail(), "Activation code", welcomeMessage);
        }
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

    public List<AppUser> findAllUsers() {
        return userRepo.findAll();
    }

    public void saveUser(AppUser user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet()); //create set of roles to check data for new roles from form

        user.getRoles().clear();

        for(String key : form.keySet()){
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepo.save(user);
    }

    public void updateProfile(AppUser user, String password, String email) {
        String userCurrentEmail = user.getEmail();
        boolean isEmailChanged = email != null && !email.equals(userCurrentEmail)
                || (userCurrentEmail != null && userCurrentEmail.equals(email));
        if(isEmailChanged){
            user.setEmail(email);
            if(!email.isEmpty()){
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if(!password.isEmpty()){
            user.setPassword(password);
        }

        userRepo.save(user);
        if(isEmailChanged) sendMessage(user);
    }

    public boolean validateUserRequest(AppUser user) {
        return (user.getPassword() != null && user.getPassword().equals(user.getPassword2()));
    }
}
