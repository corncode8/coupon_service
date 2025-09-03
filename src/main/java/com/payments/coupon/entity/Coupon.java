package com.payments.coupon.entity;

import com.payments.support.common.BaseEntity;
import com.payments.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "coupon", indexes = {
        @Index(name = "idx_user_type_unique", columnList = "user_id, type", unique = true)
})
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CouponType type;

    @Builder
    public Coupon(User user, CouponType type) {
        this.user = user;
        this.type = type;
    }

}


