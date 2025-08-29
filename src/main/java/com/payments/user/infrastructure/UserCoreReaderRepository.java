package com.payments.user.infrastructure;

import com.payments.user.entity.User;
import com.payments.user.repository.UserReaderRepository;
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
