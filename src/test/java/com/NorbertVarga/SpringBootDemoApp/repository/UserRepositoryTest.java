package com.NorbertVarga.SpringBootDemoApp.repository;

import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserAccount;
import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserAddress;
import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserRoleTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private UserAccount testUser;
    private UserAddress userAddress;
    private List<UserAddress> addressList = new ArrayList<>();
    private List<UserRoleTypes> roles = new ArrayList<>();

    @BeforeEach
    public void init() {
        roles.add(UserRoleTypes.ROLE_USER);

        userAddress = new UserAddress(
                        "testCountry",
                        "testCity",
                        "zipcode",
                        "testStreet",
                        10,
                        "additional info"
                );
        userAddress.setCreatedAt(LocalDateTime.now());
        userAddress.setLastModified(LocalDateTime.now());
        addressList.add(userAddress);

        testUser = new UserAccount(
                "test",
                "user",
                "test@email.com",
                true,
                5000,
                roles,
                addressList
        );
        testUser.setCreatedAt(LocalDateTime.now());
        testUser.setLastModified(LocalDateTime.now());
        testUser.setPassword("test1234");
    }

    @DisplayName("Test for saving a User")
    @Test
    public void givenUser_whenSave_thenReturnSavedUser() {

        // ** GIVEN

        // ** WHEN
        UserAccount savedUser = userRepository.save(testUser);

        // ** THEN
        assertNotNull(savedUser);
        assertNotNull(savedUser.getUserId());
        assertTrue(savedUser.getUserId() > 0);

    }

    @DisplayName("Test for findAll User")
    @Test
    public void given3Users_whenFindAll_thenUserListSizeEquals3() {

        // ** GIVEN
        UserAccount testUser2 = new UserAccount(
                "test",
                "user",
                "test2@email.com",
                true,
                5000,
                roles,
                addressList
        );
        testUser2.setCreatedAt(LocalDateTime.now());
        testUser2.setLastModified(LocalDateTime.now());
        testUser2.setPassword("test1234");

        UserAccount testUser3 = new UserAccount(
                "test",
                "user",
                "test3@email.com",
                true,
                5000,
                roles,
                addressList
        );
        testUser3.setCreatedAt(LocalDateTime.now());
        testUser3.setLastModified(LocalDateTime.now());
        testUser3.setPassword("test1234");

        userRepository.save(testUser);
        userRepository.save(testUser2);
        userRepository.save(testUser3);

        // ** WHEN
        List<UserAccount> savedUsers = userRepository.findAll();

        // ** THEN
        assertNotNull(savedUsers);
        assertEquals(3, savedUsers.size());
    }

    @DisplayName("Test for successful findByEmail")
    @Test
    public void givenUser_whenFindByEmail_thenReturnUser() {
        // ** GIVEN
        userRepository.save(testUser);

        // ** WHEN
        Optional<UserAccount> userOptional = userRepository.findByEmail("test@email.com");
        UserAccount userByEmail = userOptional.get();

        // ** THEN
        assertNotNull(userByEmail);
    }

    @DisplayName("Test for unsuccessful findByEmail")
    @Test
    public void givenUser_whenFindByEmail_thenReturnNull() {
        // ** GIVEN
        userRepository.save(testUser);

        // ** WHEN
        // Check the email string which is not correct, so we will not find that user.
        Optional<UserAccount> userByEmail = userRepository.findByEmail("test2@email.com");

        // ** THEN
        assertTrue(userByEmail.isEmpty());
    }

    @DisplayName("Test for update User")
    @Test
    public void givenUser_whenUpdateUser_thenReturnUpdatedUser() {
        // ** GIVEN
        userRepository.save(testUser);

        // ** WHEN
        UserAccount userForUpdate = userRepository.findByEmail("test@email.com").get();
        userForUpdate.setBalance(10000);
        userForUpdate.setFirstName("updated");
        userForUpdate.setLastName("updated");
        UserAccount updatedUser = userRepository.save(userForUpdate);

        // ** THEN
        assertEquals(10000, updatedUser.getBalance());
        assertEquals("updated", updatedUser.getFirstName());
        assertEquals("updated", updatedUser.getLastName());
    }

    @DisplayName("Test for delete user")
    @Test
    public void given2User_whenDelete1User_thenReturnUserListWithSize1() {

        // ** GIVEN
        UserAccount testUser2 = new UserAccount(
                "test",
                "user",
                "test2@email.com",
                true,
                5000,
                roles,
                addressList

        );
        testUser2.setCreatedAt(LocalDateTime.now());
        testUser2.setLastModified(LocalDateTime.now());
        testUser2.setPassword("test1234");

        userRepository.save(testUser);
        userRepository.save(testUser2);

        // ** WHEN
        UserAccount userToDelete = userRepository.findByEmail("test@email.com").get();
        userRepository.delete(userToDelete);

        Optional<UserAccount> deletedUser = userRepository.findByEmail("test@email.com");
        List<UserAccount> userList = userRepository.findAll();

        // ** THEN
        assertTrue(deletedUser.isEmpty());
        assertEquals(1, userList.size());
    }
}
