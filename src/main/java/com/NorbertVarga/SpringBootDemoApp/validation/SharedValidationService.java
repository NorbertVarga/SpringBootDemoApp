package com.NorbertVarga.SpringBootDemoApp.validation;


import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserAccount;
import com.NorbertVarga.SpringBootDemoApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class SharedValidationService {

    private final UserRepository userRepository;

    @Autowired
    public SharedValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserAccount findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean isEmailAlreadyExist(String email) {
        Optional<UserAccount> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent();
    }

    public boolean isStringEmpty(String input) {
        return (input == null || input.isEmpty() || input.isBlank());
    }

}
