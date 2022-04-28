package com.stpr.piupitter.web_controller;


import com.stpr.piupitter.data.model.user.AppUser;
import com.stpr.piupitter.data.model.user.Role;
import com.stpr.piupitter.data.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserRepo userRepo;

    @GetMapping
    public String allUsers(Model model){
        model.addAttribute("users", userRepo.findAll());
        return "all_users";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable AppUser user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "edit_user";
    }

    @PostMapping
    public String updateUser(@RequestParam String username,
                                 @RequestParam Map<String, String> form,
                                 @RequestParam("userId") AppUser user){
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
        return "redirect:/user";
    }
}
