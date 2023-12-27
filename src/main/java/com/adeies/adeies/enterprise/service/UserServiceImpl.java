package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.mappers.UpdateEmployeeMapper;
import com.adeies.adeies.enterprise.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private final UpdateEmployeeMapper updateEmployeeMapper = UpdateEmployeeMapper.INSTANCE;

    @Autowired
    private UserRepo userRepo;

    @Override
    public User createUser(User req) {
        return userRepo.save(req);
    }
}
