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
        TWO_FACTOR_AUTH(1),
        SETUP_TWO_FACTOR_AUTH(2);

        private final long bit;

        SettingBits(long bit) {
            this.bit = bit;
        }

    }

}
