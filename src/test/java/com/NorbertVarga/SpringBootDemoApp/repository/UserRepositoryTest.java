package com.NorbertVarga.SpringBootDemoApp.repository;

import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserAccount;
import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserAddress;
import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserRoleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @DisplayName("Test for saving a User")
    @Test
    public void givenUser_whenSave_returnSavedUser() {

        // given
        UserAddress userAddress =
                new UserAddress(
                        "testCountry",
                        "testCity",
                        "zipcode",
                        "testStreet",
                        10,
                        "additional info"
                );
        userAddress.setCreatedAt(LocalDateTime.now());
        userAddress.setLastModified(LocalDateTime.now());

        UserAccount testUser = new UserAccount(
                "test",
                "user",
                "test@email.com",
                true,
                5000,
                List.of(UserRoleTypes.ROLE_USER),
                List.of(userAddress)
        );
        testUser.setCreatedAt(LocalDateTime.now());
        testUser.setLastModified(LocalDateTime.now());
        testUser.setPassword("test1234");

        // when
        UserAccount savedUser = userRepository.save(testUser);

        // then
        assertNotNull(testUser);
        assertEquals(1, savedUser.getUserId());

    }
}
