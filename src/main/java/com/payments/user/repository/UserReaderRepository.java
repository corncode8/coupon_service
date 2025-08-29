package com.payments.user.repository;

import com.payments.user.entity.User;

import java.util.Optional;

public interface UserReaderRepository {

    Optional<User> findById(Long id);
}
