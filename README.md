# Spring Boot Demo Web-shop
### Welcome visitor and thank you for paying attention to my work.

# Global introduction
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



- ### REST
- ### Faker API



# Usage


## API Documentation

### - USER ENDPOINTS

### - ADMIN ENDPOINTS