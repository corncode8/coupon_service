package com.payments.user.domain.infrastructure;


import com.payments.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserJpaRepository extends JpaRepository<User, Long> {
}
