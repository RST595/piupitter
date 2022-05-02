package com.stpr.piupitter.data.repository;

import com.stpr.piupitter.data.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser findAppUserByUsername(String email);

    AppUser findByActivationCode(String code);
}
