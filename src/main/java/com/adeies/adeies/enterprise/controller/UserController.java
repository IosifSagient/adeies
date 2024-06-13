package com.adeies.adeies.enterprise.controller;

import com.adeies.adeies.enterprise.dto.user.ChangePasswordRq;
import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.repository.UserRepo;
import com.adeies.adeies.enterprise.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/employees/create")
    public ResponseEntity<User> createEmployee(@RequestBody User user) {
        User res = userService.createEmployee(user);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/user-details")
    public ResponseEntity<User> getEmployee(@RequestParam("userId") String userId) {
        return  ResponseEntity.ok(userRepo.findById(Long.parseLong(userId)).get());
    }
    @PatchMapping
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRq request,
                                            Principal connectedUser) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
