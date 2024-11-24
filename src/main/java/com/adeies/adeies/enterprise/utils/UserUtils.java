package com.adeies.adeies.enterprise.utils;

import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.exception.ValidationFaultException;
import com.adeies.adeies.enterprise.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserUtils {

    @Autowired
    private UserRepo userRepo;
    public User getUserFromOAuth(OAuth2User oAuth2User){
        return userRepo.findByEmail(oAuth2User.getAttribute("email")).orElseThrow(
                () -> new ValidationFaultException("1234", "User not found"));
    }
}
