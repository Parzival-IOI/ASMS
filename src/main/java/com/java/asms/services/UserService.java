package com.java.asms.services;

import com.java.asms.dtos.dtoUser.request.UserRequest;
import com.java.asms.dtos.dtoUser.response.UserResponse;
import com.java.asms.enums.LoginStatus;
import com.java.asms.models.Login;
import com.java.asms.models.Student;
import com.java.asms.models.User;
import com.java.asms.models.Year;
import com.java.asms.repositories.LoginRepository;
import com.java.asms.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;

    public UserResponse createUser(UserRequest userRequest) {
        User user = User.builder()
                        .firstName(userRequest.getFirstName())
                        .lastName(userRequest.getLastName())
                        .dob(userRequest.getDob())
                        .email(userRequest.getEmail())
                        .address(userRequest.getAddress())
                        .role(userRequest.getRole())
                        .phone(userRequest.getPhone())
                        .nationalId(userRequest.getNationalId())
                        .build();

        loginRepository.save(Login.builder()
                .username(userRequest.getUsername())
                .password(new BCryptPasswordEncoder().encode(userRequest.getPassword()))
                .isStudent(false)
                .isBlocked(false)
                .attempt(0)
                .status(LoginStatus.ENABLE)
                .user(user)
                .build());

        userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.responseUser(user, userRequest.getUsername());
        return userResponse;
    }

    public UserResponse getUserById(long id) {
        User user = userRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found."));
        UserResponse userResponse = new UserResponse();
        userResponse.responseUser(user, user.getLogin().getUsername());
        return userResponse;
    }


    public UserResponse updateUserById(long id, UserRequest userRequest) {
        User user = userRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found."));
        userRequest.requestUser(user);

        Login login = user.getLogin();
        login.setUsername(userRequest.getUsername());
        if(!userRequest.getPassword().isEmpty()) {
            login.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
        }

        loginRepository.save(login);

        User updatedUser = userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.responseUser(updatedUser, user.getLogin().getUsername());
        return userResponse;
    }

    public void deleteUserById(long id) {
        User user = userRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found."));
        loginRepository.delete(user.getLogin());
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
            userResponse.responseUser(user, user.getLogin().getUsername());
            userResponseList.add(userResponse);
        }
        return userResponseList;
    }
}
