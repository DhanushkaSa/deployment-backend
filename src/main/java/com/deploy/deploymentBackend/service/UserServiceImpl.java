package com.deploy.deploymentBackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deploy.deploymentBackend.entity.User;
import com.deploy.deploymentBackend.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User updateUser(String id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setAge(user.getAge());
            return userRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public void deleteUser(String id) {

        userRepository.deleteById(id);
    }

}
