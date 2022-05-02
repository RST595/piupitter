package com.stpr.piupitter.web_controller;

import com.stpr.piupitter.data.model.user.AppUser;
import com.stpr.piupitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/registration")
    public String addNewUSer(AppUser user, Map<String, Object> model){
        if(!userService.addUser(user)){
            model.put("message", "User with such email already registered");
            return "registration";
        }
        return "redirect: /login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){

        if(userService.activateUser(code)){
            model.addAttribute("message", "You successfully activate your profile");
        } else {
            model.addAttribute("message", "Activation code isn't founded");
        }


        return "login";
    }
}
