package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User createUser(User user);
}
