package com.turtlemint.authservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {
    /**
     * Ignore nano seconds and then check
     * @param first
     * @param second
     * @return
     */
    public static boolean same(LocalDateTime first, LocalDateTime second){
        return first.minusNanos(first.getNano()).equals(second.minusNanos(second.getNano()));
    }
}
