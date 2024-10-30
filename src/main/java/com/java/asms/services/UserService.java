package com.java.asms.services;

import com.java.asms.dtos.dtoUser.request.UserRequest;
import com.java.asms.dtos.dtoUser.response.UserResponse;
import com.java.asms.models.User;
import com.java.asms.models.Year;
import com.java.asms.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    public UserResponse createUser(UserRequest userRequest) {
        User user = new User();
        userRequest.requestUser(user);
        User savedUser = userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.responseUser(savedUser);
        return userResponse;
    }

    public UserResponse getUserById(long id) {
        User user = userRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found."));
        UserResponse userResponse = new UserResponse();
        userResponse.responseUser(user);
        return userResponse;
    }


    public UserResponse updateUserById(long id, UserRequest userRequest) {
        User user = userRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found."));
        userRequest.requestUser(user); 
        User updatedUser = userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.responseUser(updatedUser);
        return userResponse;
    }

    public void deleteUserById(long id) {
        User user = userRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found."));
        userRepository.delete(user);
    }

    public List<UserResponse> getAllUser(Integer pageNo, Integer pageSize, String sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> userPage = userRepository.findAll(pageable);
        List<User> userList = userPage.getContent();
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user: userList){
            UserResponse userResponse = new UserResponse();
            userResponse.responseUser(user);
            userResponseList.add(userResponse);
        }
        return userResponseList;
    }
}
