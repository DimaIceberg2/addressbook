package com.example.adressbook.service;

import com.example.adressbook.entity.User;
import com.example.adressbook.dto.request.UserCreateRequest;
import com.example.adressbook.dto.response.UserResponse;
import com.example.adressbook.mapper.UserMapper;
import com.example.adressbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElse(null);
    }

    @Transactional
    public UserResponse create(UserCreateRequest request) {
        log.info("Creating user: {}", request);

        // Проверка уникальности email
        if (userRepository.findByEmailAddress(request.emailAddress()).isPresent()) {
            throw new RuntimeException("User with email " + request.emailAddress() + " already exists");
        }

        User user = userMapper.toEntity(request);
        user = userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponse update(Long id, UserCreateRequest request) {
        log.info("Updating user with id: {}", id);

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existingUser.setName(request.name());
        existingUser.setSecondName(request.secondName());
        existingUser.setPatronymic(request.patronymic());
        existingUser.setPhoneNumber(request.phoneNumber());
        existingUser.setEmailAddress(request.emailAddress());
        existingUser.setBirthDate(request.birthDate());

        if (request.address() != null) {
            existingUser.setAddress(userMapper.toEntity(request).getAddress());
        }

        existingUser = userRepository.save(existingUser);
        return userMapper.toResponse(existingUser);
    }

    @Transactional
    public void deleteById(Long id) {
        log.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }
}
