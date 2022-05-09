package com.stpr.piupitter.service;

import com.stpr.piupitter.configuration.PasswordEncoder;
import com.stpr.piupitter.data.model.user.AppUser;
import com.stpr.piupitter.data.repository.UserRepo;
import org.checkerframework.checker.units.qual.A;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

import static com.stpr.piupitter.data.model.user.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean //mockBean emulate field bellow, so in out test we don't cover testing of userRepo methods (such findByUsername etc.)
    private UserRepo userRepo;

    @MockBean //mockBean emulate field bellow, so in out test we don't cover testing sending email
    private MailSender mailSender;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void addValidUserAndExpectOK() {
        AppUser user = new AppUser();
        user.setEmail("test@test.test");

        assertTrue(userService.addUser(user));
        assertNotNull(user.getActivationCode());
        assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(USER)));

        verify(userRepo, Mockito.times(1)).save(user); //checking what mock of userRepo was called one time with save method
        verify(mailSender, Mockito.times(1))
                .send(ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.eq("Activation code"),
                        ArgumentMatchers.contains("Welcome to piupitter."));
    }

    @Test
    void addExistingUserAndExpectFail() {
        AppUser user = new AppUser();
        user.setUsername("Max");

        doReturn(new AppUser())
                .when(userRepo)
                .findAppUserByUsername("Max");
        assertFalse(userService.addUser(user));

        verify(userRepo, Mockito.times(0)).save(any(AppUser.class));
        verify(mailSender, Mockito.times(0))
                .send(ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
    }

    @Test
    void activateUserAndExpectOk() {
        AppUser user = new AppUser();
        user.setActivationCode("someCode");
        doReturn(user)
                .when(userRepo)
                .findByActivationCode("activate");
        assertTrue(userService.activateUser("activate"));
        assertNull(user.getActivationCode());
        verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    void activateUserAndExpectGail() {
        assertFalse(userService.activateUser("activate me"));
        verify(userRepo, Mockito.times(0)).save(any(AppUser.class));

    }
}