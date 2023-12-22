package com.oskarwiedeweg.cloudwork;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BitUtils {

    public boolean hasBit(Long bits, Long bit) {
        return (bits & bit) == bit;
    }

    public Long addBit(Long bits, Long bit) {
        return bits | bit;
    }

    public Long removeBit(Long bits, Long bit) {
        return bits & ~bit;
    }

    @Getter
    public enum SettingBits {
        // 2FA
        TWO_FACTOR_AUTH(0b0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0001L),
        SETUP_TWO_FACTOR_AUTH(0b0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0010L),

        // Notifications
        NOTIFICATIONS_ALL(0b0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_1111_1111_1111_0000_0000_0000L),
        NOTIFICATIONS_ENABLED(0b0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_1000_0000_0000_0000_0000_0000L),
        NOTIFICATION_TEST(0b0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0001_0000_0000_0000L);

        private final long bit;

        SettingBits(long bit) {
            this.bit = bit;
        }

    }

}
