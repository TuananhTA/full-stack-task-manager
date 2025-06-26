package com.petd.profileservice.repository;

import com.petd.profileservice.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {

  Optional<Account> findByEmail(String email);

}
