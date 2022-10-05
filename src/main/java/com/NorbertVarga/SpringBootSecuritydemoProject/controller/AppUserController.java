package com.NorbertVarga.SpringBootSecuritydemoProject.controller;

import com.NorbertVarga.SpringBootSecuritydemoProject.dto.AppUserData_DTO;
import com.NorbertVarga.SpringBootSecuritydemoProject.dto.UserCreateCommand;
import com.NorbertVarga.SpringBootSecuritydemoProject.dto.UserUpdateCommand;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.AppUser;
import com.NorbertVarga.SpringBootSecuritydemoProject.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService userService;

    @Autowired
    public AppUserController(AppUserService userService) {
        this.userService = userService;
    }

    // UNSECURED "free" endpoints for the registration and login
    @PostMapping("/register")
    public ResponseEntity<AppUserData_DTO> registerUser(@RequestBody UserCreateCommand command) {
        AppUserData_DTO registeredUserData = userService.registerUser(command);
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
    public ResponseEntity<AppUserData_DTO> updateUser(@RequestBody UserUpdateCommand command) {
        AppUserData_DTO updatedUserData = userService.updateUser(command);
        ResponseEntity<AppUserData_DTO> response = null;
        if (updatedUserData != null) {
            response = new ResponseEntity<>(updatedUserData, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping("/delete/me")
    public ResponseEntity<Void> deleteUser() {
        ResponseEntity<Void> response;
        boolean deleteWasSuccess = userService.deleteUser();
        if (deleteWasSuccess) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
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
        ResponseEntity<AppUserData_DTO> response;
        if (userData != null) {
            response = new ResponseEntity<>(userData, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<AppUserData_DTO> updateUserById(@PathVariable(value = "id") Long id, @RequestBody UserUpdateCommand command) {
        AppUserData_DTO updatedUserData = userService.updateUserById(id, command);
        ResponseEntity<AppUserData_DTO> response;
        if (updatedUserData != null) {
            response = new ResponseEntity<>(updatedUserData, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable(value = "id") Long id) {
        ResponseEntity<Void> response;
        boolean deleteWasSuccess = userService.deleteUserById(id);
        if (deleteWasSuccess) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
}
