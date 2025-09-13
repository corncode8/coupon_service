package com.payments.user.domain.repository;

import com.payments.user.domain.entity.User;

public interface UserStoreRepository {

    User save(User user);
}
