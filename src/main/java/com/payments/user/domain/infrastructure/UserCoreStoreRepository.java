package com.payments.user.domain.infrastructure;

import com.payments.user.domain.entity.User;
import com.payments.user.domain.repository.UserStoreRepository;
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
