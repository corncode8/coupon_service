package com.payments.reservation.entity;

import com.payments.support.common.BaseEntity;
import com.payments.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "reservation")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "time", nullable = false, length = 50)
    private TimeSlice time;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
