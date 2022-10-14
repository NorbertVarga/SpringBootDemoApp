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
- ### Validation
- ### REST
- ### Faker API



# Usage


## API Documentation

### - USER ENDPOINTS

### - ADMIN ENDPOINTS