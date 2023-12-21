package com.oskarwiedeweg.cloudwork.notifications;

import com.oskarwiedeweg.cloudwork.BitUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    TEST(BitUtils.SettingBits.NOTIFICATION_TEST.getBit(), "Das ist ein Test.", "Aber mit Parameter %s");

    private final Long settingsBit;
    private final String title;
    private final String body;

}
