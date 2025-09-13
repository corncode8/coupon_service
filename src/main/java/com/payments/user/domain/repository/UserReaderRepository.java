package com.payments.user.domain.repository;

import com.payments.user.domain.entity.User;

import java.util.Optional;

public interface UserReaderRepository {

    Optional<User> findById(Long id);
}
