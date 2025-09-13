package com.payments.user.entity;

import com.payments.support.common.BaseEntity;
import com.payments.coupon.entity.Coupon;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "point", nullable = false)
    private Long point;

    @OneToMany
    @JoinColumn(name = "coupon_id")
    private List<Coupon> coupons = new ArrayList<>();


    @Builder
    public User(Long point, List<Coupon> coupons) {
        this.point = point;
        this.coupons = coupons;
    }

}
