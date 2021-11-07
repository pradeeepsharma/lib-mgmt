package com.learning.LibraryManagementSystem.service;

import com.learning.LibraryManagementSystem.exception.UserNotFoundException;
import com.learning.LibraryManagementSystem.models.User;
import com.learning.LibraryManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User getUser(long userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty())
            throw new UserNotFoundException(userId);
        return userOptional.get();
    }

    public List<User> getUsers(){
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public void removeUser(Long userId){
        userRepository.deleteById(userId);
    }

    public User addUser(User user){
        return userRepository.save(user);
    }
}
