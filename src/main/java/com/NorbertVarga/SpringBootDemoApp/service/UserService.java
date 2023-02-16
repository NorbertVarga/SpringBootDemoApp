package com.NorbertVarga.SpringBootDemoApp.service;

import com.NorbertVarga.SpringBootDemoApp.dto.userAccount.UserFullData_DTO;
import com.NorbertVarga.SpringBootDemoApp.dto.userAccount.UserCreateCommand;
import com.NorbertVarga.SpringBootDemoApp.dto.userAccount.UserUpdateCommand;
import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserAccount;
import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserPrincipal;
import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserRoleTypes;
import com.NorbertVarga.SpringBootDemoApp.errorHandling.UserBalanceNotEnoughException;
import com.NorbertVarga.SpringBootDemoApp.faker.FakerService;
import com.NorbertVarga.SpringBootDemoApp.repository.UserRepository;
import com.NorbertVarga.SpringBootDemoApp.validation.SharedValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder pwEncoder;
    private final SharedValidationService validationService;
    private final FakerService faker;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final Logger purchaselogger = LoggerFactory.getLogger("purchaseLog");

    public UserService(UserRepository userRepository, PasswordEncoder pwEncoder, SharedValidationService validationService, FakerService faker) {
        this.userRepository = userRepository;
        this.pwEncoder = pwEncoder;
        this.validationService = validationService;
        this.faker = faker;
        populateDataBaseWithDummyUsers(3);
        saveAdminUser();
        saveSimpleUser();
    }

    //  ** UNSECURED Registration method reachable for anybody
    public UserFullData_DTO registerUser(UserCreateCommand command) {
        UserAccount registeredUser = null;
        if (!validationService.isEmailAlreadyExist(command.getEmail())) {
            UserAccount user = new UserAccount(command);
            user.setPassword(pwEncoder.encode(command.getPassword()));
            registeredUser = userRepository.save(user);
            logger.info("** USER REGISTERED: " + registeredUser.getEmail() + "! NAME: " + registeredUser.getFirstName() + " " + registeredUser.getLastName());
        } else {
            logger.warn("** FAILED REGISTER: " + command.getEmail() + " already exist!");
            throw new EntityExistsException("The given email is already taken.");
        }

        return new UserFullData_DTO(registeredUser);
    }

    //  **  CRUD methods for "myAccount" (the actually logged in user)   ////////////////////////////////////////////////////////////////
    public UserFullData_DTO getMyAccountData() {
        UserAccount user = getLoggedInUser();
        return new UserFullData_DTO(user);
    }

    public UserFullData_DTO updateUser(UserUpdateCommand command) {
        UserAccount user = getLoggedInUser();
        UserFullData_DTO updatedUserData;
        if (user != null) {

            if (!validationService.isStringEmpty(command.getEmail())) {
                if (!validationService.isEmailAlreadyExist(command.getEmail())) {
                    user.setEmail(command.getEmail());
                } else {
                    throw new EntityExistsException("The given email is already exist");
                }
            }

            if (!validationService.isStringEmpty(command.getFirstName())) {
                user.setFirstName(command.getFirstName());
            }
            if (!validationService.isStringEmpty(command.getLastName())) {
                user.setLastName(command.getLastName());
            }
            if (!validationService.isStringEmpty(command.getPassword())) {
                user.setPassword(pwEncoder.encode(command.getPassword()));
            }

            UserAccount updatedUser = userRepository.save(user);
            updatedUserData = new UserFullData_DTO(updatedUser);
        } else {
            throw new EntityNotFoundException("There is no user logged in");
        }
        return updatedUserData;
    }

    public void deleteUser() {
        UserAccount user = getLoggedInUser();
        if (user != null) {
            userRepository.delete(user);
        } else {
            throw new EntityNotFoundException("There is no user logged in for delete");
        }
    }///////////////////////////////////////////////////////////////////////////////////////////////////


    //  **  CRUD methods by id (only for ADMIN role) ////////////////////////////////////////////////////////////////
    public List<UserFullData_DTO> getAllUsersData() {
        List<UserAccount> users = userRepository.findAll();
        return users.stream()
                .map(UserFullData_DTO::new).collect(Collectors.toList());
    }

    public UserFullData_DTO getUserDataById(Long id) {
        UserFullData_DTO userData;
        Optional<UserAccount> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userData = new UserFullData_DTO(userOptional.get());
        } else {
            throw new EntityNotFoundException("There is no user with the given id");
        }
        return userData;
    }

    public UserFullData_DTO updateUserById(Long id, UserUpdateCommand command) {
        UserFullData_DTO updatedUserData;
        Optional<UserAccount> userOptinal = userRepository.findById(id);
        if (userOptinal.isPresent()) {
            UserAccount userForUpdate = userOptinal.get();
            if (!validationService.isStringEmpty(command.getEmail())) {
                if (!validationService.isEmailAlreadyExist(command.getEmail())) {
                    userForUpdate.setEmail(command.getEmail());
                } else {
                    throw new EntityExistsException("The given email is already exist");
                }
            }

            if (!validationService.isStringEmpty(command.getFirstName())) {
                userForUpdate.setFirstName(command.getFirstName());
            }
            if (!validationService.isStringEmpty(command.getLastName())) {
                userForUpdate.setLastName(command.getLastName());
            }
            if (!validationService.isStringEmpty(command.getPassword())) {
                userForUpdate.setPassword(pwEncoder.encode(command.getPassword()));
            }

            UserAccount updatedUser = userRepository.save(userForUpdate);
            updatedUserData = new UserFullData_DTO(updatedUser);
        } else {
            throw new EntityNotFoundException("There is no user with the given Id");
        }
        return updatedUserData;
    }

    public void deleteUserById(Long id) {
        Optional<UserAccount> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserAccount userForDelete = userOptional.get();
            userRepository.delete(userForDelete);
        } else {
            throw new EntityNotFoundException("There is no User with the given Id");
        }

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////


    //  ** UTILS  //////////////////////////////////////////////////////////////////////////

    public UserAccount getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal loggedInUser = (UserPrincipal) authentication.getPrincipal();
        return userRepository.findByEmail(loggedInUser.getUsername()).orElseThrow(EntityNotFoundException::new);
    }

    public UserAccount findUserByEmail(String email) {
        UserAccount user = null;
        Optional<UserAccount> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            if (userOptional.get().isEnabled()) {
                user = userOptional.get();
            }
        } else {
            throw new EntityNotFoundException("There is no account with the given email.");
        }
        return user;
    }

    public UserAccount findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
    }

    public void decreaseBalance(UserAccount user, int price) {
        if (user.getBalance() >= price) {
            purchaselogger.info(" ** USER BALANCE DECREASE: " + user.getEmail() + " | " + user.getBalance() + " - " + price + " = " + (user.getBalance() - price));
            user.setBalance(user.getBalance() - price);
            purchaselogger.info("User new balance: " + user.getBalance());
            userRepository.save(user);
        } else {
            throw new UserBalanceNotEnoughException("The User don't have enough money for the purchase");
        }
    }

    //      --  DATABASE INITIATING --  (Methods called in the Constructor)
    // Populate database with dummy users
    private void populateDataBaseWithDummyUsers(int count) {
        List<UserAccount> users = faker.createDummyUsers(count, pwEncoder);
        userRepository.saveAll(users);
    }

    // Populate database with an ADMIN user
    private void saveAdminUser() {
        UserAccount adminUser = new UserAccount();
        adminUser.setEmail("admin@email.com");
        adminUser.setFirstName("admin");
        adminUser.setLastName("user");
        adminUser.setEnabled(true);
        adminUser.setBalance(1000000);
        adminUser.setPassword(pwEncoder.encode("test1234"));
        adminUser.setRoles(List.of(UserRoleTypes.ROLE_USER, UserRoleTypes.ROLE_ADMIN));
        adminUser.setAddressList(faker.generateRandomCountOfAddresses());
        userRepository.save(adminUser);
    }

    // Populate database with a "simple" user
    private void saveSimpleUser() {
        UserAccount simpleUser = new UserAccount();
        simpleUser.setEmail("simple.user@email.com");
        simpleUser.setFirstName("Simple");
        simpleUser.setLastName("User");
        simpleUser.setEnabled(true);
        simpleUser.setBalance(50000);
        simpleUser.setPassword(pwEncoder.encode("test1234"));
        simpleUser.setRoles(List.of(UserRoleTypes.ROLE_USER));
        simpleUser.setAddressList(faker.generateRandomCountOfAddresses());
        userRepository.save(simpleUser);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
}
