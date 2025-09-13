package com.payments.user.domain.infrastructure;

import com.payments.user.domain.entity.User;
import com.payments.user.domain.repository.UserReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserCoreReaderRepository implements UserReaderRepository {
    private final UserJpaRepository repository;

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }
}
