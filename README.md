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
So I hope you can easily try the project in action. A detailed description about the usage can be found below.*

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

**I will give you specific details about the validation contrainst we use in the API Documentation section.**


- ### Faker API
Faker is very simple API to generate some random data. Like names, emails, addresses etc.       
It's useful when you're developing a new project and need some pretty data for showcase.        
In the project we use it to populate our database with some dummy users and products with random properties.      

**https://github.com/DiUS/java-faker**        
**http://dius.github.io/java-faker/apidocs/index.html**


***
# Usage

You can clone the repository to your local machine and run it from an IDE by starting the *SpringBootDemoApplication.java*       
When the program starts the Faker API generate some dummy users and products and automatically populate our H2 DB.      
I also put a specified account with ROLE_USER and another one with ROLE_ADMIN.           
You can use that specified accounts to log in, or you can register new account via the API.    

## Functionality
There is only a very basic functionality implemented for now.

**I recommend for you the Postman application for use and test manually the API**

The registration flow is unsecure, we have to allow our visitors to make are accounts.     
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
but basically its only allowed for and ADMIN)*       
- Find user by id
- Update a user by id
- Delete a user by id
- Create a new product
- Update a product by id
- Delete a product by id
- Get all the purchases data from all users
- Get all the purchases from a User by id
- Get all the product orders from all users        
*(a product order is a kind of entry which includes which products we want to buy and how much we want from that.    
One purchase can include more product orders)*
***

# API Documentation
In that section I will give you detailed information about the API,         
the specified validation constrains and how you can use it.      
If you are familiar with Postman application, I export the whole collection for you.      
Download it, and import to your Postman.      
[Download it, and import to your Postman.](src/main/resources/SpringBootDemoWebshop.postman_collection.json)


### UNSECURED "FREE" ENDPOINTS:
- Login 




### USER ENDPOINTS







### - ADMIN ENDPOINTS