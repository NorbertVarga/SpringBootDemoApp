package com.NorbertVarga.SpringBootSecuritydemoProject.faker;

import com.NorbertVarga.SpringBootSecuritydemoProject.entity.AppUser;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.UserRoleTypes;
import com.github.javafaker.Faker;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Faker is used to generate some dummy Data.
 */
public class FakerService {

    private static final Faker faker = new Faker();

    public static List<AppUser> createDummyUsers(int count, PasswordEncoder pwEncoder) {
        List<AppUser> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            AppUser fakeUser = generateDummyUser();
            fakeUser.setPassword(pwEncoder.encode("test1234"));
            users.add(fakeUser);
        }
        return users;
    }

    private static AppUser generateDummyUser() {
        String firstname = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = firstname + "." + lastName + "@email.com";
        String password = "test1234";
        List<UserRoleTypes> roles = List.of(UserRoleTypes.ROLE_USER);

        return new AppUser(firstname,lastName,email,true, roles);
    }
}
