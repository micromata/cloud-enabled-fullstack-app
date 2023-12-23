package com.oskarwiedeweg.cloudwork.notifications;

import com.oskarwiedeweg.cloudwork.BitUtils;
import com.oskarwiedeweg.cloudwork.notifications.dto.EnableNotificationsDto;
import com.oskarwiedeweg.cloudwork.user.User;
import com.oskarwiedeweg.cloudwork.user.UserDao;
import lombok.Data;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.jose4j.lang.JoseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Data
@Service
public class NotificationsService {

    private final PushService pushService;
    private final UserDao userDao;
    private final NotificationsDao notificationsDao;

    public void sendNotification(User user, NotificationType notificationType, Object[] titleParams, Object[] descriptionParams) {
        if (!BitUtils.hasBit(user.getSettings(), notificationType.getSettingsBit())) {
            return;
        }

        Subscription subscription = loadUserSubscription(user.getId());
        if (subscription == null) {
            return;
        }

        String payload = """
                {
                    "title": "%s",
                    "description": "%s"
                }
                """
                .formatted(notificationType.getTitle().formatted(titleParams), notificationType.getBody().formatted(descriptionParams));

        try {
            Notification notification = new Notification(subscription, payload);
            pushService.send(notification);
        } catch (GeneralSecurityException | IOException | JoseException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void subscribe(Long userId, EnableNotificationsDto body) {
        User user = userDao.findUserById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean hadSubscription = loadUserSubscription(userId) != null;
        notificationsDao.saveUserConnection(userId, body.getEndpoint(), body.getKey(), body.getAuth());

        if (!hadSubscription) {
            userDao.updateUserSettings(userId,
                    BitUtils.addBit(
                            user.getSettings(),
                            BitUtils.SettingBits.NOTIFICATIONS_ALL.getBit())
            );
        }
    }

    public void unsubscribe(Long userId) {
        User user = userDao.findUserById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        notificationsDao.deleteUserConnection(userId);
        userDao.updateUserSettings(userId,
                BitUtils.removeBit(
                        user.getSettings(),
                        BitUtils.SettingBits.NOTIFICATIONS_ALL.getBit())
        );
    }

    private Subscription loadUserSubscription(Long user) {
        Optional<UserNotificationEndpoint> userConnection = notificationsDao.getUserConnection(user);
        if (userConnection.isEmpty()) {
            return null;
        }
        UserNotificationEndpoint userNotificationEndpoint = userConnection.get();

        return new Subscription(
                userNotificationEndpoint.getEndpoint(),
                new Subscription.Keys(
                        userNotificationEndpoint.getKey(),
                        userNotificationEndpoint.getAuth()
                )
        );
    }

}
