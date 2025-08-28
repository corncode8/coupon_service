package com.payments.domain.reservation.infrastructure;

import com.payments.domain.reservation.repository.ReservationStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationCoreStoreRepository implements ReservationStoreRepository {
    private final ReservationJpaRepository repository;
}
