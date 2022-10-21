# Spring Boot Demo Web-shop
### Welcome visitor! Thank you for paying attention to my work.

***
# Global introduction

The project is an implementation of a very small and simple working web-shop.     
Users can register, login, browse the products and make some purchases.      
I try to follow the REST principles when building the Project.      
The entire project was built from scratch by my own hands and is continuously under development.    
This is only a BackEnd oriented project without any kind of UI included.
The project is not installed on any network!           
You can freely clone the repository and try it out on a local machine. 

**If you want to use the program you will need a basic Development Environment:**
- **IDE** (IntelliJ, Eclipse, etc.) for manage or editing the code if you need
- **API Development Tool** (ex: Postman) for testing the API since there is no frontend
- **Java 17**
- **Maven**
- **Optional: MY-SQL or any DB connection tool**   
  (We basically use a built-in H2 in-memory database, so you don't need to care about it)

*If you are here, I assume that you are a developer, and you have some relevant experience in IT.     
So I hope you can easily try the project in action. A detailed description about the usage can be found below.*  [Usage](#Usage)

**Please notice that this is a hobby project, and it doesn't want to represent any kind of real business value.**      
There is some functionality which are less realistic or professional.    
(For example there is no realistic money logic or any kind of currency system, we just play around with simple integers.)      
Sometimes it does not follow the general user requirements    
and in some places, it may contain seemingly unnecessary, or less relevant features.

**The main goal was the practicing and the self development.         
I just wanted to make a simple and well-functioning system at the basic level in SpringBoot environment**.

***
# Technological Features

- ### SpringBoot    
> Spring Boot provides a good platform for Java developers to develop a stand-alone and production-grade spring application that you can just run.    
> You can get started with minimum configurations without the need for an entire Spring configuration setup.     
> Spring Boot automatically configures your application based on the dependencies you have added to the project.       
> https://www.tutorialspoint.com/spring_boot/spring_boot_introduction.htm

- ### Spring Security
> Spring Security is a framework that provides authentication, authorization, and protection against common attacks.    
> With first class support for securing both imperative and reactive applications,     
> it is the de-facto standard for securing Spring-based applications.      
> https://docs.spring.io/spring-security/reference/index.html

**I use basic authentication flow. Users can register and login with their email address.    
They have their own session with their own shopping cart stored in it for the lifecycle of that session(1Hour).**

**I separate a simple user role and an admin role with extended operations.**     
(For example the users can't see any information about each other, but if you are logged in as an admin you can get all the information about "your users").    
*You will find more details about it in the USAGE / API documentation section.*

Basically in the SecurityConfig, we allow any users to call any endpoint after proper authentication.     
``` java
    http.authorizeRequests()
      .antMatchers("/**").permitAll()
      .anyRequest().authenticated();
```
But I secure the endpoints with simple method security in controller level.     
``` java
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/me")
    public ResponseEntity<UserFullData_DTO> getMyAccount() {
        UserFullData_DTO userData = userService.getMyAccountData();
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }
```
``` java
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/all")
    public ResponseEntity<List<UserFullData_DTO>> getAllUsersData() {
        List<UserFullData_DTO> usersDataList = userService.getAllUsersData();
        return new ResponseEntity<>(usersDataList, HttpStatus.OK);
    }
```


- ### H2 In-Memory DB
> H2 is an embedded, open-source, and in-memory database. It is a relational database management system written in Java.  
> It is a client/server application. It stores data in memory, not persist the data on disk.     
> https://www.geeksforgeeks.org/spring-boot-h2-database/

**You can access to that in-memory database while the program is running.**      
Run the program --> open a browser --> write URL: `http://localhost:8080/h2`    
The starting point of the project is -- > **SpringBootDemoApplication.java**

![H2 login page](/src/main/resources/pictures/h2_login.png "H2 login page")

- ### Hibernate, JPA
> A JPA (Java Persistence API) is a specification of Java which is used to access, manage, and persist data between Java object and relational database.    
> It is considered as a standard approach for Object Relational Mapping.        
> JPA can be seen as a bridge between object-oriented domain models and relational database systems.     
> Being a specification, JPA doesn't perform any operation by itself.     
> Thus, it requires implementation. So, ORM tools like Hibernate, TopLink, and iBatis implements JPA specifications for data persistence.

> A Hibernate is a Java framework which is used to store the Java objects in the relational database system.   
> It is an open-source, lightweight, ORM (Object Relational Mapping) tool.     
> Hibernate is an implementation of JPA. So, it follows the common standards provided by the JPA.     
> https://www.javatpoint.com/jpa-vs-hibernate

- ### Validation
> Validating user input is a super common requirement in most applications.    
> And the Java Bean Validation framework has become the de facto standard for handling this kind of logic.      
> https://www.baeldung.com/javax-validation    
> https://docs.oracle.com/javaee/7/api/javax/validation/constraints/package-summary.html

In the project we use the javax validation API, hibernate-validator, and javax expression language.     
There is a kind of "multi-level" validation flow with the consistent constraints.     
We are validating the user commands which are comes to the controller endpoints in the body of the request.      
Easily we can do this with some simple annotations on the fields in the incoming DTO.     
``` java
public class UserCreateCommand {

    @Size(min = 3, max = 30, message
            = "First name must be between {min} and {max} characters")
    @NotBlank (message = "Empty string not allowed here!")
    private String firstName;

    @Size(min = 3, max = 30, message
            = "Last name must be between {min} and {max} characters")
    @NotBlank (message = "Empty string not allowed here!")
    private String lastName;

    @Size(min = 10, max = 80, message
            = "Email must be between {min} and {max} characters")
    @NotBlank (message = "Empty string not allowed here!")
    private String email;

    @Size(min = 8, max = 40, message
            = "Password must be between {min} and {max} characters")
    @NotBlank (message = "Empty string not allowed here!")
    private String password;
    
    // rest of the code...
    }
```
Or we can implement a validator class where we can validate some more complex custom constraints.   
(For example we can check email, or a password format with regex, or some business logic etc.)
``` java
@Component
public class UserCreateCommandValidator implements Validator {

    private final SharedValidationService validationService;

    @Autowired
    public UserCreateCommandValidator(SharedValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCreateCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateCommand command = (UserCreateCommand) target;

        if (!command.getEmail().matches("^(.+)@(.+)$")) {
            errors.rejectValue("email", "email.valid");
        }

        if (validationService.findUserByEmail(command.getEmail()) != null) {
            errors.rejectValue("email", "email.exist");
        }
    }
}
```
And we can put the same constraints annotation to the fields in our entities to ensure that we do the same validations in the database level as well.      

**I will give you specific details about the validation constraints we use in the API Documentation section.**
[Api Documentation](#API Documentation)     

- ### Liquibase Database versioning
> Liquibase is a database schema change management solution.    
> Liquibase enables you to revise and release database changes faster and safer from development to production.    
> Liquibase uses SQL, XML, JSON, and YAML changelog files to list database changes in sequential order.    
> Database changes have the format of changesets. Changesets contain Change Type,    
> which are types of operations to apply to the database, such as adding a column or primary key.     
> Context, label, and precondition changelog tags help precisely control when a database change is made and to which database environment it is deployed.     
> https://docs.liquibase.com/concepts/introduction-to-liquibase.html     
> https://www.baeldung.com/liquibase-refactor-schema-of-java-app

- ### Faker API
Faker is very simple API to generate some random data. Like names, emails, addresses etc.       
It's useful when you're developing a new project and need some pretty data for showcase.        
In the project we use it to populate our database with some dummy users and products with random properties.      

**https://github.com/DiUS/java-faker**        
**http://dius.github.io/java-faker/apidocs/index.html**
***    


# Usage

You can clone the repository to your local machine and run it from an IDE by starting the `SpringBootDemoApplication.java`              
When the program starts the Faker API generate some dummy users and products and automatically populate our H2 DB.      
**Notice that the generated users and products will be different in every rerun since we generate data randomly.**          
I also put a specified account with ROLE_USER and another one with ROLE_ADMIN.           
You can use that specified accounts to log in, or you can register new account via the API.    

## Functionality

There is only a very basic functionality implemented for now.       
**I recommend for you the Postman application for use and test manually the API**       
The registration flow is unsecure, we have to allow our visitors to make an account.     
After the success registration the new account will be saved to our DB with the simple USER role.     

**As a simple USER you can do the operations below:**
- Login
- Get information about your profile
- Update your profile 
- Delete your profile (delete is NOT only logical, we exactly delete that account from the DB and is not exist anymore.)    
- Logout
- List all the products
- Operations for Cart(put products, remove products, get your cart info, clear your cart)
- Make purchase (get the products from your cart and "buy" those)
- Get all your purchases data

**As an ADMIN you have the same functionality with your account and the purchases.    
And you have some additional operations what you can do only with ADMIN role.**   
- Get all users   
*(for development purposes I comment out the `@Secured` annotation from that endpoint,    
so you can get the list of the users without any kind of login or authentication,     
but basically its only allowed for ADMIN)*       
- Find user by id
- Update a user by id
- Delete a user by id (delete is NOT only logical, we exactly delete that account from the DB and is not exist anymore.)      
- Create a new product
- Update a product by id
- Delete a product by id
- Get all the purchases data from all users
- Get all the purchases from a User by id
- Get all the product orders from all users        
*(a product order is a kind of entry which includes which products we want to buy and how much we want from that.    
One purchase can include more product orders)*

### Cart      
Every user have their own Cart stored in the session for the lifecycle of that session(1Hour).        
Users can put and remove products from the cart and also can get the data of their Cart.        
If a user logging out we are invalidating his session so the Cart will be cleared.

### Purchase Logic        
If we make a purchase the program will pull the product orders from the cart.    
**We have to take care about the product quantities!**     
Several users can add the same product to their basket at the same time,   
and we cannot know whether there is still enough quantity from the given product for the purchase.      
The program will check it, and will update the quantities before the purchase item is generated.       

*For example if we have ten pieces in the cart from a product which has a total quantity of six,     
then the purchase item will include only six piece from that product.     
If there is no stock from a product we totally remove that order from the purchase.*   

Finally, from these valid and manipulated product orders will be generated the purchase item which stands for "one transaction".    
The program will calculate automatically the total price for the purchase.     
We check the User balance before we make the purchase, for sure if the user don't have enough money the purchase will not be completed.     
Otherwise, if the purchase was successful we clear the Cart.    
After a successful purchase the program will decrease the balance of the user with the price of the purchase,     
and also will update the total quantity of the products.
***

# API Documentation
In that section I will give you detailed information about the API,         
the specified validation constrains and how you can use it.      
Since the API more-or-less RESTFUL we only communicate with JSON-s.         
If you are familiar with Postman application, I export the whole collection for you.       
[Download it, and import to your Postman.](src/main/resources/SpringBootDemoWebshop.postman_collection.json)


### UNSECURED "FREE" ENDPOINTS:

#### Register:     
`http://localhost:8080/api/users/register` **POST**      

*Register a new account with a simple USER role. The registered user will get a default balance: 20000*    

**Constraints:**     
- firstName and lastName: *Must be between 3 and 30 characters and cannot be null, empty or blank string.*     
- email: *Must be between 10 and 80 characters, must contain a '@' character and cannot be null, empty or blank string.*       
- password: *Must be between 8 and 40 characters and cannot be null, empty or blank string.*       

*In the future maybe I will separate the address creation logic, for now we have to add an address for the registration.*        
*Address:*      
- country and city: *Minimum 3 characters and cannot be null, empty or blank string.*     
- zipcode and street: *Cannot be null, empty or blank string.*     
- houseNumber: *Cannot be null.*    
- additionalInfo: *Maximum 500characters.*

**A valid registration command looks like this:**       

``` json
{
  "firstName": "registered",  
  "lastName": "user",
  "email": "registered@email.com",
  "password": "test1234",
  "address": {
    "country": "Somecountry",
    "city": "somecity",
    "zipcode": "1157",
    "street": "teststreet",
    "houseNumber": 34,
    "additionalInfo": "Some optional additional info for the address"
  }
}
```


### SECURED USER ENDPOINTS    

### Login           
`http://localhost:8080/api/users/login` **GET**      

*Basic authentication flow require an authorization header with Username and password.     
In our case the username is always the email address of the account.*

You can log in with all the generated dummy users (you can get the list of all users).    
Or you can log in with the specified USER or ADMIN account.     
The password for the generated users is always: `test1234`    

- **Admin login:**      
username: `admin@email.com`     
password: `test1234`       
                    

- **User login:**     
username: `simple.user@email.com`     
password: `test1234`
      

### My Account        
`http://localhost:8080/api/users/me` **GET**       

You will get detailed information about your account and addresses.
    
        
### Update My Account      
`http://localhost:8080/api/update/me` **PUT**        

*You can update your profile. For now this functionality is so simple and maybe not that professional.       
You can update your first name, last name, email address, and password.*    

**Constraints**     
For this operation you can leave any of the fields empty or blank.        
I only check validation for the fields which contains some data.     

- firstName and lastName: *Must be between 3 and 30 characters*          
- email: *Must be between 10 and 80 characters and must contain a '@' character*            
- password: *Must be between 8 and 40 characters*           

If you wanted to only update your first name, then you can leave the other fields blank.    
The program will update only the fields which contain some data.        

A valid command which only update the first name and the password looks like this:     
*The last name and email will remain the original in that case*
``` JSON
{
    "firstName": "updatedName",
    "lastName": "",
    "email": "",
    "password": "newPassword"
}
```

**Notice that: All the fields what you fill with data must be valid.     
If one of them is invalid the whole update will not be executed!      
So for example this is an invalid command which will failed and don't update anything since the names are not valid.**
``` JSON
{
    "firstName": "a", 
    "lastName": "ku",
    "email": "updated@email.com",
    "password": "newPassword"
}
```
       
### Delete My Account      
`http://localhost:8080/api/users/delete/me` **DELETE**         
*You can delete your account*     

This is again a functionality which could be more professional.     
**Now this deleting operation an exact delete, so we remove the user from the database and this account is no more exist.**     

This should be only a "logical" delete where we still leave the account in the DB,     
and just indicate somehow that user is deleted, (put it to inactive state, delete the sensitive data etc.)

### Logout     
`http://localhost:8080/api/users/logout` **GET**     
*You will be logged out and your session will invalidate, and it's mean your shopping cart also will be cleared.*    

### List all products     
`http://localhost:8080/api/products/all` **GET**          
*You can get the list of all products*    

### Find a product by id       
`http://localhost:8080/api/products/find/5` **GET**      
*You can get some details about a specified product by id.*     
     
I use the `@PathVariable` annotation here, the last number in that URL stands for the id of the product.     
When you start the program it will generate 30 different products.     
`.../products/find/1-30`     

### Add one product to the Cart by id
`http://localhost:8080/api/cart/add-one-product/22` **GET**      
*You can put only one piece from a product to your cart*

We use the `@PathVariable` annotation here, the last number in that URL stands for the id of the product.   
When you start the program it will generate 30 different products.     
`.../add-one-product/1-30`           
     
### Add more products to the Cart by id       
`http://localhost:8080/api/cart/add-more-product?productId=3&quantity=5` **GET**     
*You can put more pieces of a specified product to your cart*      
     
Here we use simple parameters in the URL.      
`...?productId=1-30&...`      

**Notice that: in that point there is no restriction, or limit for the quantity.     
You can put to your cart a higher quantity from a product than the actual stock is.     
Later the program will check it when you initiate a purchase.**     
     
### Remove one product from the cart by id      
`http://localhost:8080/api/cart/remove-one-product/1` **GET**      
*You can remove one piece of a specified product from the cart by id.*
     
The logic is the same as the add product method. We use `@PathVariable` to identify the product by the id.       
*Don't forget we generate 30products* `.../remove-one/product/1-30` 
      
### Remove more products from the cart by id      
`http://localhost:8080/api/cart/remove-more-product?productId=28&quantity=19`  **GET**         
*You can remove more pieces of a specified product by id.*      

The logic is the same as the add more product method. We use simple parameters here as well.      
If you remove more pieces from a product than you put in cart, the program will remove the whole product order entry.      
     
### Remove product order from the cart by id       
`http://localhost:8080/api/cart/remove-one-order/2` **GET**      
*You can remove a full product order entry from the cart by the id of that product, regardless of the quantity*

Here we use again the `@PathVariable` annotations. The number at the end of the URL stands for the product id;      
`.../remove-one-order/1-30`     
      
### Clear cart     
`http://localhost:8080/api/cart/clear-cart` **GET**       
*You can remove all the product orders and totally clear the cart*      
      
### Make purchase     
`http://localhost:8080/api/purchase/make-purchase` **GET**     
*You can initiate a purchase and buy the products from your cart.*    

You can find detailed information about the purchase logic above. [Purchase Logic](#Purchase Logic)      
     
### List my purchases     
`http://localhost:8080/api/purchase/my-purchases` **GET**      
*You can list all of your purchases what you make before*     



### SECURED ADMIN ENDPOINTS     
*As an ADMIN you have extended options. You can manage the products and also the users.*     

These endpoints are secured and only accessible with an account which has the role of ADMIN. `@Secured({"ROLE_ADMIN"})`    

### List all Users    
`http://localhost:8080/api/users/all`  **GET**     
*You can list all the users registered and saved in the database.*

**Notice that for some developer purposes I just comment out the `@Secured` annotation from that endpoint.     
So that's mean you can call that endpoint without any kind of login or authentication.**      
    
### Find a User by id     
`http://localhost:8080/api/users/find/1` **GET**      
*You can find, and get some information about a user by id.*     

Again I use the `@PathVariable` annotation here. The number at the end of the URL stands for the user id.        
We generate 10 dummy users, a specified "simple" user, and a specified ADMIN user.       
So when we start the program there is a total 12 users. `.../find/1-12`      
    
### Update a user by id     
`http://localhost:8080/api/users/update/1` **PUT**       
*As an ADMIN, you have the option to overwrite user data*     

The constraints are the same as the simple update user method what I describe above. [Update My Account](#Update My Account)    
     
### Delete a user by id     
`http://localhost:8080/api/users/delete/3` **DELETE**     
*As an ADMIN you have the option to delete a user by its id.*    

**The logic is the same as the simple delete my account method, this is NOT only a logical delete.     
This will exactly remove the users from the database.**    

### Create Product   
`http://localhost:8080/api/products/create` **POST**     
*As an ADMIN you have to option to create and save new product to the database.*    

**Constraints**    
- name: *Must be between 3 and 80 characters and cannot be empty or blank string.*   
- description: *Maximum 1000characters.*    
- price: *Cannot be null and must be a positive whole number.*  
- quantity: *Cannot be null and must be a positive whole number.*   

A valid command looks like this:    
```json
{
"name": "Custom created product",
"description": "Some description here",
"tags": ["food", "meat", "admin_OPS", "someMoreTag"],
"price": 15,
"totalQuantity": 6
}
```

*Tags are optional*   

### Update product by id       
`http://localhost:8080/api/products/update/5` **PUT**      
*As an ADMIN you can update the properties of a specified product by id.*       
        
**Constraints**       
- name: *Must be between 3 and 80characters*      
- description: *Maximum 1000characters*
- price and totalQuantity: *Must be positive whole number*      

Here again we use the `@PathVariable` annotation in the end of the url. *Don't forget we have 30 products*      
`.../update/1-30`     
      
The logic is nearly the same as the user update method. If you don't write data for a field that field will not be updated.      
For example this is a valid command which only will update the price of a product:      
```json
{
    "name": "",
    "description": "     ",
    "price": 1000,
    "totalQuantity": null
}
```

### Delete product by id      
`http://localhost:8080/api/products/delete/4` **DELETE**     
*As an ADMIN you have the option to delete a specified product by id*       

Here again we use the `@PathVariable` annotation in the end of the url. *Don't forget we have 30 products*      
`.../delete/1-30`    

This is again NOT only a logical delete, the product will be totally removed from the database and no more existing.      
   
### List all purchases     
`http://localhost:8080/api/purchase/get-all` **GET**      
*As an ADMIN you can list all the purchases from all the users*      
    
Basically there is no purchases in the system. You have to log in with a user, put something to your cart and initiate some purchases.     
After that you have to log in with the ADMIN user and get that purchases back.     

### List purchases by User      
`http://localhost:8080/api/purchase/get-all-by-user/12` **GET**      
*As an ADMIN you can get all the purchases from a specified User by id.*      

Here again we use the `@PathVariable` annotation in the end of the url. *Don't forget we have 12 users*      
`.../get-all-by-user/1-12`     
     
Don't forget there is no purchases in the system. As I describe above you have to log in with a user and make some.     


Thank you for follow my work!      
Feel free to clone and use this repository. I hope you can find some useful technic or code snippets in it.      
This is only a Junior level small application, but it was so enjoyable to build it.     
I just play around a little with the system of the SpringBoot in a very basic level.    