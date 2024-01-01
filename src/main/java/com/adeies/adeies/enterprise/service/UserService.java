package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.user.ChangePasswordRequest;
import com.adeies.adeies.enterprise.entities.User;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface UserService {

    User createUser(User user);

    void changePassword(ChangePasswordRequest request, Principal connectedUser);
}
