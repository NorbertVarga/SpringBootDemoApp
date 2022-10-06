package com.NorbertVarga.SpringBootSecuritydemoProject.service;

import com.NorbertVarga.SpringBootSecuritydemoProject.dto.AppUserData_DTO;
import com.NorbertVarga.SpringBootSecuritydemoProject.dto.UserCreateCommand;
import com.NorbertVarga.SpringBootSecuritydemoProject.dto.UserUpdateCommand;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.AppUser;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.UserPrincipal;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.UserRoleTypes;
import com.NorbertVarga.SpringBootSecuritydemoProject.faker.FakerService;
import com.NorbertVarga.SpringBootSecuritydemoProject.repository.AppUserRepository;
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
public class AppUserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder pwEncoder;

    public AppUserService(AppUserRepository userRepository, PasswordEncoder pwEncoder) {
        this.userRepository = userRepository;
        this.pwEncoder = pwEncoder;
        populateDataBaseWithDummyUsers(10);
        saveAdminUser();
        saveSimpleUser();
    }

    //  ** UNSECURED Registration method reachable for anybody
    public AppUserData_DTO registerUser(UserCreateCommand command) {
        AppUser registeredUser = null;
        if (!checkEmailExistAlready(command.getEmail())) {
            AppUser user = new AppUser(command);
            user.setPassword(pwEncoder.encode(command.getPassword()));
            registeredUser = userRepository.save(user);
        } else {
            throw new EntityExistsException("The given email is already taken.");
        }

        return new AppUserData_DTO(registeredUser);
    }

    //  **  CRUD methods for "myAccount" (the actually logged in user)   ////////////////////////////////////////////////////////////////
    public AppUserData_DTO getMyAccountData() {
        AppUser user = getLoggedInUser();
        return new AppUserData_DTO(user);
    }

    public AppUserData_DTO updateUser(UserUpdateCommand command) {
        AppUser user = getLoggedInUser();
        AppUserData_DTO updatedUserData = null;
        if (user != null) {
            if (!checkEmailExistAlready(command.getEmail())) {
                user.setEmail(command.getEmail());
            } else {
                throw new EntityExistsException("The given email is already exist");
            }
            user.setLastName(command.getLastName());
            user.setFirstName(command.getFirstName());
            user.setPassword(pwEncoder.encode(command.getPassword()));

            AppUser updatedUser = userRepository.save(user);
            updatedUserData = new AppUserData_DTO(updatedUser);
        } else {
            throw new EntityNotFoundException("There is no user logged in");
        }
        return updatedUserData;
    }

    public void deleteUser() {
        AppUser user = getLoggedInUser();
        if (user != null) {
            userRepository.delete(user);
        } else {
            throw new EntityNotFoundException("There is no user logged in for delete");
        }
    }///////////////////////////////////////////////////////////////////////////////////////////////////


    //  **  CRUD methods by Id (only for ADMIN role) ////////////////////////////////////////////////////////////////
    public List<AppUserData_DTO> getAllUsers() {
        List<AppUser> users = userRepository.findAll();
        return users.stream()
                .map(AppUserData_DTO::new).collect(Collectors.toList());
    }

    public AppUserData_DTO findUserById(Long id) {
        AppUserData_DTO userData = null;
        Optional<AppUser> userOptinal = userRepository.findById(id);
        if (userOptinal.isPresent()) {
            userData = new AppUserData_DTO(userOptinal.get());
        } else {
            throw new EntityNotFoundException("There is no user with the given id");
        }
        return userData;
    }

    public AppUserData_DTO updateUserById(Long id, UserUpdateCommand command) {
        AppUserData_DTO updatedUserData = null;
        Optional<AppUser> userOptinal = userRepository.findById(id);
        if (userOptinal.isPresent()) {
            AppUser userForUpdate = userOptinal.get();

            if (!checkEmailExistAlready(command.getEmail())) {
                userForUpdate.setEmail(command.getEmail());
            } else {
                throw new EntityExistsException("The given email is already exist");
            }
            userForUpdate.setLastName(command.getLastName());
            userForUpdate.setFirstName(command.getFirstName());
            userForUpdate.setPassword(pwEncoder.encode(command.getPassword()));

            AppUser updatedUser = userRepository.save(userForUpdate);
            updatedUserData = new AppUserData_DTO(updatedUser);
        } else {
            throw new EntityNotFoundException("There is no user with the given Id");
        }
        return updatedUserData;
    }

    public void deleteUserById(Long id) {
        Optional<AppUser> userOptinal = userRepository.findById(id);
        if (userOptinal.isPresent()) {
            AppUser userForDelete = userOptinal.get();
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
    public boolean checkEmailExistAlready(String email) {
        Optional<AppUser> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent();
    }

    public AppUser getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal loggedInUser = (UserPrincipal) authentication.getPrincipal();
        return userRepository.findByEmail(loggedInUser.getUsername()).orElseThrow(EntityNotFoundException::new);
    }

    public AppUser findUserByEmail(String email) {
        AppUser user = null;
        Optional<AppUser> userOptional = userRepository.findByEmail(email);
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
        List<AppUser> users = FakerService.createDummyUsers(count, pwEncoder);
        userRepository.saveAll(users);
    }

    // Populate database with an ADMIN user
    private void saveAdminUser() {
        AppUser adminUser = new AppUser();
        adminUser.setEmail("admin@email.com");
        adminUser.setFirstName("admin");
        adminUser.setLastName("user");
        adminUser.setEnabled(true);
        adminUser.setPassword(pwEncoder.encode("test1234"));
        adminUser.setRoles(List.of(UserRoleTypes.ROLE_USER, UserRoleTypes.ROLE_ADMIN));
        userRepository.save(adminUser);
    }

    // Populate database with a "simple" user
    private void saveSimpleUser() {
        AppUser simpleUser = new AppUser();
        simpleUser.setEmail("norbertVarga@email.com");
        simpleUser.setFirstName("norbert");
        simpleUser.setLastName("varga");
        simpleUser.setEnabled(true);
        simpleUser.setPassword(pwEncoder.encode("test1234"));
        simpleUser.setRoles(List.of(UserRoleTypes.ROLE_USER));
        userRepository.save(simpleUser);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
}
