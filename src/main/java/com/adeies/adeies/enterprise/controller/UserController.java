package com.adeies.adeies.enterprise.controller;

import com.adeies.adeies.enterprise.dto.user.ChangePasswordRq;
import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User res = userService.createUser(user);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @PatchMapping
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRq request,
                                            Principal connectedUser) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
