package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.user.ChangePasswordRequest;
import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.mappers.UpdateEmployeeMapper;
import com.adeies.adeies.enterprise.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UpdateEmployeeMapper updateEmployeeMapper = UpdateEmployeeMapper.INSTANCE;

    @Autowired
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User req) {
        return userRepo.save(req);
    }

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken)connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        userRepo.save(user);
    }
}
