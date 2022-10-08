package com.NorbertVarga.SpringBootDemoApp.entity.userAccount;

import com.NorbertVarga.SpringBootDemoApp.dto.userAccount.UserCreateCommand;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "user_account")
// Entity Listener needed for auditing and automatically manage the creating and modified dates
@EntityListeners(AuditingEntityListener.class)
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_first_name")
    @Size(min = 3, max = 30, message
            = "First name must be between {min} and {max} characters")
    @NotBlank
    private String firstName;

    @Column(name = "user_last_name")
    @Size(min = 3, max = 30, message
            = "Last name must be between {min} and {max} characters")
    @NotBlank
    private String lastName;

    @Column(name = "user_email")
    @Size(min = 10, max = 80, message
            = "Email must be between {min} and {max} characters")
    @NotBlank
    private String email;

    @Column(name = "user_password")
    @NotBlank
    private String password;

    @Column(name = "user_enabled")
    private boolean enabled;

    @Column(name = "user_balance")
    @Min(0)
    private Integer balance;

    @Column(name = "user_created_at")
    @CreatedDate
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "user_last_modified")
    @LastModifiedDate
    @NotNull
    private LocalDateTime lastModified;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = UserRoleTypes.class,
            fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role")
    @Column(name = "user_role")
    @Fetch(value = FetchMode.SUBSELECT)
    @NotEmpty
    private List<UserRoleTypes> roles;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @NotNull
    @Size(max = 3, message = "The maximum size of that list is {max}")
    private List<UserAddress> addressList = new ArrayList<>();


    public UserAccount() {
    }

    // Used by the faker to generate dummy users.
    public UserAccount(String firstName, String lastName, String email, boolean enabled, Integer balance, List<UserRoleTypes> roles, List<UserAddress> addressList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enabled = enabled;
        this.balance = balance;
        this.roles = roles;
        this.addressList = addressList;
    }

    // User constructor for manual creating with POST method.
    public UserAccount(UserCreateCommand command) {
        this.firstName = command.getFirstName();
        this.lastName = command.getLastName();
        this.email = command.getEmail();
        this.enabled = true;
        this.balance = 20000;
        this.roles = Collections.singletonList(UserRoleTypes.ROLE_USER);
        this.addressList = List.of(new UserAddress(command.getAddressCreateCommand()));
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRoleTypes> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleTypes> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public List<UserAddress> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<UserAddress> addressList) {
        this.addressList = addressList;
    }
}
