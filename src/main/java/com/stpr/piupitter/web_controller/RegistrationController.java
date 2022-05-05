package com.stpr.piupitter.web_controller;

import com.stpr.piupitter.data.model.dto.CaptchaResponseDto;
import com.stpr.piupitter.data.model.user.AppUser;
import com.stpr.piupitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    private final UserService userService;

    @Value("${recaptcha.secret}")
    private String captchaSecret;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/registration")
    public String addNewUSer(@RequestParam("password2") String passwordConfirm,
                             @RequestParam("g-recaptcha-response") String captchaResponse,
                             @Valid AppUser user,
                             BindingResult bindingResult,
                             Model model){
        //captcha block
        String url = String.format(CAPTCHA_URL, captchaSecret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        if(!response.isSuccess()){
            model.addAttribute("captchaError", "Fill captcha");
        }

        if(passwordConfirm.isEmpty()){
            model.addAttribute("password2Error", "Password confirm cannot be empty");
        }
        if(!userService.validateUserRequest(user, passwordConfirm)){
            model.addAttribute("password2Error", "Passwords are different!");
            return "registration";
        }

        if(passwordConfirm.isEmpty() || bindingResult.hasErrors() || !response.isSuccess()){
            model.mergeAttributes(ControllerUtils.getErrors(bindingResult));
            return "registration";
        }
        if(!userService.addUser(user)){
            model.addAttribute("usernameError", "User with such email already registered");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){

        if(userService.activateUser(code)){
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "You successfully activate your profile");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code isn't founded");
        }


        return "login";
    }
}
