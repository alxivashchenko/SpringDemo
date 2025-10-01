package com.ivashchenko.springdemo.service;

import com.ivashchenko.springdemo.repository.User;
import com.ivashchenko.springdemo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        Optional<User> optionalUser = userRepository.findUserByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new IllegalStateException("User with email " + user.getEmail() + " already exists");
        }
        user.setAge(Period.between(user.getBirth(), LocalDate.now()).getYears());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("User with id " + id + " does not exist");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public User updateUser(Long id, String email, String name) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("User with id " + id + " does not exist");
        }
        User user = optionalUser.get();
        if (email != null && !email.equals(user.getEmail())) {
            Optional<User> foundByEmail = userRepository.findUserByEmail(email);
            if (foundByEmail.isPresent()) {
                throw new IllegalStateException("User with email " + user.getEmail() + " already exists");
            }
            user.setEmail(email);
        }

        if (name != null && !name.equals(user.getName())) {
            user.setName(name);
        }

//        user.setEmail(email);
//        user.setName(name);

        return user;
//        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("User with id " + id + " does not exist");
        }
        User user2 = optionalUser.get();
        user2.setAge(Period.between(user.getBirth(), LocalDate.now()).getYears());
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setBirth(user.getBirth());
        return userRepository.save(user2);
    }


}
