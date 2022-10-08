package com.NorbertVarga.SpringBootDemoApp.validation;


import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserAccount;
import com.NorbertVarga.SpringBootDemoApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
