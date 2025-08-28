package com.payments.domain.user.infrastructure;

import com.payments.domain.user.repository.UserStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCoreStoreRepository implements UserStoreRepository {
    private final UserJpaRepository repository;
}
