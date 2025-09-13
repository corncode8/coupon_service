package com.payments.user.infrastructure;

import com.payments.user.entity.User;
import com.payments.user.repository.UserStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCoreStoreRepository implements UserStoreRepository {
    private final UserJpaRepository repository;

    public User save(User user) {
        return repository.save(user);
    }
}
