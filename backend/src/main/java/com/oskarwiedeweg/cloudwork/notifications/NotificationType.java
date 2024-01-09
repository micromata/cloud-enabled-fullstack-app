package com.oskarwiedeweg.cloudwork.notifications;

import com.oskarwiedeweg.cloudwork.BitUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    NEW_FOLLOWER(BitUtils.SettingBits.NOTIFICATION_NEW_SUBSCRIBER.getBit(), "New follower!", "%s just subscribed to your profile.");

    private final Long settingsBit;
    private final String title;
    private final String body;

}
