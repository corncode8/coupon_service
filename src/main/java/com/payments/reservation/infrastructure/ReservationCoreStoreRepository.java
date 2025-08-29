package com.payments.reservation.infrastructure;

import com.payments.reservation.repository.ReservationStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationCoreStoreRepository implements ReservationStoreRepository {
    private final ReservationJpaRepository repository;
}
