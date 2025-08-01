package com.deploy.deploymentBackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deploy.deploymentBackend.entity.User;

@Service
public interface UserService {

    public User createUser(User user);

    public List<User> getAllUser();

    public User getUserById(String id);

    public User updateUser(String id, User user);

    public void deleteUser(String id);

}
