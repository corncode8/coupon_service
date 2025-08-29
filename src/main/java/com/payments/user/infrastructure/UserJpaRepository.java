package com.payments.user.infrastructure;


import com.payments.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserJpaRepository extends JpaRepository<User, Long> {
}
