package com.stpr.piupitter.web_controller;


import com.stpr.piupitter.data.model.user.AppUser;
import com.stpr.piupitter.data.model.user.Role;
import com.stpr.piupitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String allUsers(Model model){
        model.addAttribute("users", userService.findAllUsers());
        return "all_users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable AppUser user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "edit_user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String editUserByAdmin(@RequestParam String username,
                                 @RequestParam Map<String, String> form,
                                 @RequestParam("userId") AppUser user){
        userService.saveUser(user, username, form);
        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal AppUser user){
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(@AuthenticationPrincipal AppUser user,
                                @RequestParam String password,
                                @RequestParam String email){
        userService.updateProfile(user, password, email);
        return "redirect: /user/profile";
    }
}
