package com.NorbertVarga.SpringBootSecuritydemoProject.controller;

import com.NorbertVarga.SpringBootSecuritydemoProject.dto.AppUserData_DTO;
import com.NorbertVarga.SpringBootSecuritydemoProject.dto.UserCreateCommand;
import com.NorbertVarga.SpringBootSecuritydemoProject.dto.UserUpdateCommand;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.AppUser;
import com.NorbertVarga.SpringBootSecuritydemoProject.service.AppUserService;
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
public class AppUserController {

    private final AppUserService userService;
    private final UserCreateCommandValidator userCreateCommandValidator;
    private final UserUpdateCommandValidator userUpdateCommandValidator;

    @Autowired
    public AppUserController(AppUserService userService, UserCreateCommandValidator userCreateCommandValidator, UserUpdateCommandValidator userUpdateCommandValidator) {
        this.userService = userService;
        this.userCreateCommandValidator = userCreateCommandValidator;
        this.userUpdateCommandValidator = userUpdateCommandValidator;
    }

    @InitBinder("userCreateCommand")
    protected void initBinderUserCreate(WebDataBinder binder) {
        binder.addValidators(userCreateCommandValidator);
    }

    @InitBinder("userUpdateCommand")
    protected void initBinderUserUpdate(WebDataBinder binder) {
        binder.addValidators(userUpdateCommandValidator);
    }

    @InitBinder("userUpdateCommandForAdmin")
    protected void initBinderUserUpdateForAdmin(WebDataBinder binder) {
        binder.addValidators(userUpdateCommandValidator);
    }


    // UNSECURED "free" endpoints for the registration and login
    @PostMapping("/register")
    public ResponseEntity<AppUserData_DTO> registerUser(@RequestBody @Valid UserCreateCommand userCreateCommand) {
        AppUserData_DTO registeredUserData = userService.registerUser(userCreateCommand);
        return new ResponseEntity<>(registeredUserData, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<AppUserData_DTO> loginUser() {
        ResponseEntity<AppUserData_DTO> response;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails user) {
            AppUser loggedUser = userService.findUserByEmail(user.getUsername());
            if (loggedUser != null) {
                response = new ResponseEntity<>(new AppUserData_DTO(loggedUser), HttpStatus.OK);
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
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_INACTIVE"})
    @GetMapping("/me")
    public ResponseEntity<AppUserData_DTO> getMyAccount() {
        AppUserData_DTO userData = userService.getMyAccountData();
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/update/me")
    public ResponseEntity<AppUserData_DTO> updateUser(@RequestBody @Valid UserUpdateCommand userUpdateCommand) {
        AppUserData_DTO updatedUserData = userService.updateUser(userUpdateCommand);
        return new ResponseEntity<>(updatedUserData, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping("/delete/me")
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_INACTIVE"})
    @GetMapping("/logout")
    public ResponseEntity<Void> logoutUser(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////


    //  **  SECURED ADMIN ENDPOINTS **  //////////////////////////////////////////////////////////
//    @Secured({"ROLE_ADMIN"})
    @GetMapping("/all")
    public ResponseEntity<List<AppUserData_DTO>> getAllUsersData() {
        List<AppUserData_DTO> usersDataList = userService.getAllUsers();
        return new ResponseEntity<>(usersDataList, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/find/{id}")
    public ResponseEntity<AppUserData_DTO> findUserById(@PathVariable(value = "id") Long id) {
        AppUserData_DTO userData = userService.findUserById(id);
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<AppUserData_DTO> updateUserById(@PathVariable(value = "id") Long id, @RequestBody @Valid UserUpdateCommand userUpdateCommandForAdmin) {
        AppUserData_DTO updatedUserData = userService.updateUserById(id, userUpdateCommandForAdmin);
        ResponseEntity<AppUserData_DTO> response;
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
