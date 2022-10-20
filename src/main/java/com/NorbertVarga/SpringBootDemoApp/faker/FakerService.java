package com.NorbertVarga.SpringBootDemoApp.faker;

import com.NorbertVarga.SpringBootDemoApp.entity.product.Product;
import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserAccount;
import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserAddress;
import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserRoleTypes;
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


    //  **  USER ACCOUNT **  ///////////////////////////////////////////////////////
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
        String email = (firstname + "." + lastName + "@email.com").toLowerCase();
        String password = "test1234";
        int balance = random.nextInt(70000) + 10000;
        List<UserRoleTypes> roles = List.of(UserRoleTypes.ROLE_USER);
        List<UserAddress> addressList = generateRandomCountOfAddresses();

        return new UserAccount(firstname, lastName, email, true, balance, roles, addressList);
    }

    public List<UserAddress> generateRandomCountOfAddresses() {
        int randomCount = random.nextInt(3) + 1;
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
            houseNumber = random.nextInt(300) + 1;
            additionalInfo = faker.lorem().sentence(random.nextInt(8) + 2);
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
    //////////////////////////////////////////////////////////////////////////////

    //  **  PRODUCT **  ///////////////////////////////////////////////////////
    public List<Product> createDummyProducts(int count) {
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Product product = generateDummyProduct();
            productList.add(product);
        }
        return productList;
    }

    public Product generateDummyProduct() {
        String name = faker.commerce().productName();
        String description = faker.lorem().sentence(random.nextInt(20) + 3);
        List<String> tags = faker.lorem().words(random.nextInt(5));
        int price = random.nextInt(500) + 25;
        int totalQuantity = random.nextInt(200) + 10;
        return new Product(name, description, tags, price, totalQuantity);
    }


    //////////////////////////////////////////////////////////////////////////////
}
