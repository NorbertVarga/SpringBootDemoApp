package com.NorbertVarga.SpringBootSecuritydemoProject.faker;

import com.NorbertVarga.SpringBootSecuritydemoProject.entity.UserAccount;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.UserAddress;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.UserRoleTypes;
import com.github.javafaker.Faker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Faker is used to generate some dummy Data.
 */
@Component
public class FakerService {

    private final Faker faker = new Faker();
    private Random random = new Random();

    public List<UserAccount> createDummyUsers(int count, PasswordEncoder pwEncoder) {
        List<UserAccount> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            UserAccount fakeUser = generateDummyUser();
            fakeUser.setPassword(pwEncoder.encode("test1234"));
            users.add(fakeUser);
        }
        return users;
    }

    public UserAccount generateDummyUser() {
        String firstname = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = firstname + "." + lastName + "@email.com";
        String password = "test1234";
        List<UserRoleTypes> roles = List.of(UserRoleTypes.ROLE_USER);
        List<UserAddress> addressList = generateRandomCountOfAddresses();

        return new UserAccount(firstname,lastName,email,true,roles,addressList);
    }

    public List<UserAddress> generateRandomCountOfAddresses() {
        int randomCount = random.nextInt(3)+1;
        List<UserAddress> addressList = new ArrayList<>();
        String country;
        String city;
        String zipcode;
        String street;
        int houseNumber;
        String additionalInfo;

        for (int i = 0; i < randomCount; i++) {
            country = faker.address().country();
            city = faker.address().city();
            zipcode = faker.address().zipCode();
            street = faker.address().streetName();
            houseNumber = random.nextInt(300)+1;
            additionalInfo = faker.lorem().sentence(random.nextInt(8)+2);
            UserAddress address = new UserAddress(
                    country,
                    city,
                    zipcode,
                    street,
                    houseNumber,
                    additionalInfo
            );
            addressList.add(address);
        }
        return addressList;
    }
}
