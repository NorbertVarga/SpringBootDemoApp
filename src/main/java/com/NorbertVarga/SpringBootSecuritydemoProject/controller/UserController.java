package com.NorbertVarga.SpringBootSecuritydemoProject.controller;

import com.NorbertVarga.SpringBootSecuritydemoProject.dto.userAccount.UserFullData_DTO;
import com.NorbertVarga.SpringBootSecuritydemoProject.dto.userAccount.UserCreateCommand;
import com.NorbertVarga.SpringBootSecuritydemoProject.dto.userAccount.UserUpdateCommand;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.userAccount.UserAccount;
import com.NorbertVarga.SpringBootSecuritydemoProject.service.UserService;
import com.NorbertVarga.SpringBootSecuritydemoProject.validation.UserCreateCommandValidator;
import com.NorbertVarga.SpringBootSecuritydemoProject.validation.UserUpdateCommandValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserCreateCommandValidator userCreateCommandValidator;
    private final UserUpdateCommandValidator userUpdateCommandValidator;

    @Autowired
    public UserController(UserService userService, UserCreateCommandValidator userCreateCommandValidator, UserUpdateCommandValidator userUpdateCommandValidator) {
        this.userService = userService;
        this.userCreateCommandValidator = userCreateCommandValidator;
        this.userUpdateCommandValidator = userUpdateCommandValidator;
    }

//    @InitBinder("userCreateCommand")
//    protected void initBinderUserCreate(WebDataBinder binder) {
//        binder.addValidators(userCreateCommandValidator);
//    }
//
//    @InitBinder("userUpdateCommand")
//    protected void initBinderUserUpdate(WebDataBinder binder) {
//        binder.addValidators(userUpdateCommandValidator);
//    }
//
//    @InitBinder("userUpdateCommandForAdmin")
//    protected void initBinderUserUpdateForAdmin(WebDataBinder binder) {
//        binder.addValidators(userUpdateCommandValidator);
//    }


    // UNSECURED "free" endpoints for the registration and login
    @PostMapping("/register")
    public ResponseEntity<UserFullData_DTO> registerUser(@RequestBody @Valid UserCreateCommand userCreateCommand) {
        UserFullData_DTO registeredUserData = userService.registerUser(userCreateCommand);
        return new ResponseEntity<>(registeredUserData, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<UserFullData_DTO> loginUser() {
        ResponseEntity<UserFullData_DTO> response;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails user) {
            UserAccount loggedUser = userService.findUserByEmail(user.getUsername());
            if (loggedUser != null) {
                response = new ResponseEntity<>(new UserFullData_DTO(loggedUser), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return response;
    }
    //////////////////////////////////////////////////////////////////////////////////////////


    //  **  SECURED USER ENDPOINTS **  //////////////////////////////////////////////////////////
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/me")
    public ResponseEntity<UserFullData_DTO> getMyAccount() {
        UserFullData_DTO userData = userService.getMyAccountData();
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/update/me")
    public ResponseEntity<UserFullData_DTO> updateUser(@RequestBody @Valid UserUpdateCommand userUpdateCommand) {
        UserFullData_DTO updatedUserData = userService.updateUser(userUpdateCommand);
        return new ResponseEntity<>(updatedUserData, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping("/delete/me")
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/logout")
    public ResponseEntity<Void> logoutUser(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////


    //  **  SECURED ADMIN ENDPOINTS **  //////////////////////////////////////////////////////////
//    @Secured({"ROLE_ADMIN"})
    @GetMapping("/all")
    public ResponseEntity<List<UserFullData_DTO>> getAllUsersData() {
        List<UserFullData_DTO> usersDataList = userService.getAllUsers();
        return new ResponseEntity<>(usersDataList, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/find/{id}")
    public ResponseEntity<UserFullData_DTO> findUserById(@PathVariable(value = "id") Long id) {
        UserFullData_DTO userData = userService.findUserById(id);
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<UserFullData_DTO> updateUserById(@PathVariable(value = "id") Long id, @RequestBody @Valid UserUpdateCommand userUpdateCommandForAdmin) {
        UserFullData_DTO updatedUserData = userService.updateUserById(id, userUpdateCommandForAdmin);
        return new ResponseEntity<>(updatedUserData, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable(value = "id") Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
}
