package com.payments.user.application;

import com.payments.support.exception.BaseException;
import com.payments.user.entity.User;
import com.payments.user.repository.UserReaderRepository;
import com.payments.user.repository.UserStoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.payments.support.response.BaseResponseStatus.NOT_FIND_USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserReaderRepository readerRepository;
    private final UserStoreRepository storeRepository;

    public User findById(Long id) {
        return readerRepository.findById(id)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
    }

    public User save(User user) {
        return storeRepository.save(user);
    }
}
