package com.payments.user.repository;

import com.payments.user.entity.User;

public interface UserStoreRepository {

    User save(User user);
}
