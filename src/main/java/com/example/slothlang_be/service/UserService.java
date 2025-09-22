package com.example.slothlang_be.service;

import com.example.slothlang_be.entity.User;
import com.example.slothlang_be.enums.Role;
import com.example.slothlang_be.enums.UserStatus;
import com.example.slothlang_be.exception.AppException;
import com.example.slothlang_be.exception.InValidErrorCode;
import com.example.slothlang_be.exception.UserErrorCode;
import com.example.slothlang_be.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;

    public User saveNewUser(Map<String, Object> userInfo) {
        String email = (String) userInfo.get("email");
        String googleId = (String) userInfo.get("sub");
        String fullName = (String) userInfo.get("name");            // Tên đầy đủ
        String pictureUrl = (String) userInfo.get("picture");

        User user = new User();
        user.setGoogleId(googleId);
        user.setEmail(email);
        user.setName(fullName);
        user.setImageUrl(pictureUrl);
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.CLIENT);

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new AppException(InValidErrorCode.SAVE_FAIL);
        }

    }

    public User findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId).orElse(null);
    }

    public User findById(String googleId) {
        return userRepository.findById(googleId).orElseThrow(() -> new AppException(UserErrorCode.USER_NOT_FOUND));
    }

}
