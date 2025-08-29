package com.payments.reservation.entity;

import lombok.AllArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
public class TimeSlice {
    private LocalTime start;
    private LocalTime end;

    public LocalTime getStart() {
        return start;
    }
    public LocalTime getEnd() {
        return end;
    }
}
