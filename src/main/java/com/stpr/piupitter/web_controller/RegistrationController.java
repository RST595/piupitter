package com.stpr.piupitter.web_controller;

import com.stpr.piupitter.data.model.user.AppUser;
import com.stpr.piupitter.data.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

import static com.stpr.piupitter.data.model.user.Role.USER;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepo userRepo;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUSer(AppUser user, Map<String, Object> model){
        if(userRepo.findAppUserByUsername(user.getUsername()) != null){
            model.put("message", "User with such email already registered");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(USER));
        userRepo.save(user);
        return "redirect:/login";
    }
}
