package com.NorbertVarga.SpringBootSecuritydemoProject.repository;

import com.NorbertVarga.SpringBootSecuritydemoProject.entity.userAccount.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByEmail(String email);
}
