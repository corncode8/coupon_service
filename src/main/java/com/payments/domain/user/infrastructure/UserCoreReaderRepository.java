package com.payments.domain.user.infrastructure;

import com.payments.domain.user.repository.UserReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCoreReaderRepository implements UserReaderRepository {
    private final UserJpaRepository repository;
}
