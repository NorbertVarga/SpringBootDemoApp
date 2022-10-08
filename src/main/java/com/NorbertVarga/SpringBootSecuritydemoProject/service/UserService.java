package com.NorbertVarga.SpringBootSecuritydemoProject.service;

import com.NorbertVarga.SpringBootSecuritydemoProject.dto.userAccount.UserFullData_DTO;
import com.NorbertVarga.SpringBootSecuritydemoProject.dto.userAccount.UserCreateCommand;
import com.NorbertVarga.SpringBootSecuritydemoProject.dto.userAccount.UserUpdateCommand;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.userAccount.UserAccount;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.userAccount.UserPrincipal;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.userAccount.UserRoleTypes;
import com.NorbertVarga.SpringBootSecuritydemoProject.faker.FakerService;
import com.NorbertVarga.SpringBootSecuritydemoProject.repository.UserRepository;
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
    private final FakerService faker;

    public UserService(UserRepository userRepository, PasswordEncoder pwEncoder, FakerService faker) {
        this.userRepository = userRepository;
        this.pwEncoder = pwEncoder;
        this.faker = faker;
        populateDataBaseWithDummyUsers(10);
        saveAdminUser();
        saveSimpleUser();
    }

    //  ** UNSECURED Registration method reachable for anybody
    public UserFullData_DTO registerUser(UserCreateCommand command) {
        UserAccount registeredUser = null;
        if (!isEmailAlreadyExist(command.getEmail())) {
            UserAccount user = new UserAccount(command);
            user.setPassword(pwEncoder.encode(command.getPassword()));
            registeredUser = userRepository.save(user);
        } else {
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
            if (!isEmailAlreadyExist(command.getEmail())) {
                user.setEmail(command.getEmail());
            } else {
                throw new EntityExistsException("The given email is already exist");
            }
            user.setLastName(command.getLastName());
            user.setFirstName(command.getFirstName());
            user.setPassword(pwEncoder.encode(command.getPassword()));

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


    //  **  CRUD methods by Id (only for ADMIN role) ////////////////////////////////////////////////////////////////
    public List<UserFullData_DTO> getAllUsers() {
        List<UserAccount> users = userRepository.findAll();
        return users.stream()
                .map(UserFullData_DTO::new).collect(Collectors.toList());
    }

    public UserFullData_DTO findUserById(Long id) {
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

            if (!isEmailAlreadyExist(command.getEmail())) {
                userForUpdate.setEmail(command.getEmail());
            } else {
                throw new EntityExistsException("The given email is already exist");
            }
            userForUpdate.setLastName(command.getLastName());
            userForUpdate.setFirstName(command.getFirstName());
            userForUpdate.setPassword(pwEncoder.encode(command.getPassword()));

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

    /**
     * @return "true" if the given email already exist.
     */
    public boolean isEmailAlreadyExist(String email) {
        Optional<UserAccount> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent();
    }

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

    // Populate database with dummy users in the cunstructo
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
        simpleUser.setEmail("norbertVarga@email.com");
        simpleUser.setFirstName("norbert");
        simpleUser.setLastName("varga");
        simpleUser.setEnabled(true);
        simpleUser.setBalance(20000);
        simpleUser.setPassword(pwEncoder.encode("test1234"));
        simpleUser.setRoles(List.of(UserRoleTypes.ROLE_USER));
        simpleUser.setAddressList(faker.generateRandomCountOfAddresses());
        userRepository.save(simpleUser);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
}
