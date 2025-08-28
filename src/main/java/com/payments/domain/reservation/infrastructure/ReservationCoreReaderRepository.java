package com.payments.domain.reservation.infrastructure;

import com.payments.domain.reservation.repository.ReservationReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationCoreReaderRepository implements ReservationReaderRepository {
    private final ReservationJpaRepository repository;
}
