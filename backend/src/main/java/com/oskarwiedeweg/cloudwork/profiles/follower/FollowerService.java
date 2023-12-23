package com.oskarwiedeweg.cloudwork.profiles.follower;

import com.oskarwiedeweg.cloudwork.notifications.NotificationType;
import com.oskarwiedeweg.cloudwork.notifications.NotificationsService;
import com.oskarwiedeweg.cloudwork.user.User;
import com.oskarwiedeweg.cloudwork.user.UserDao;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Data
@Service
public class FollowerService {

    private final FollowerDao followerDao;
    private final UserDao userDao;
    private final NotificationsService notificationsService;

    public int getFollowerCount(Long userId) {
        return followerDao.countFollowers(userId);
    }

    public boolean follows(Long userId, Long following) {
        return followerDao.isFollowPresent(userId, following);
    }

    public void follow(Long userId, Long followingId) {
        followerDao.createFollow(userId, followingId);

        User user = userDao.findUserById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        User following = userDao.findUserById(followingId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        notificationsService.sendNotification(following, NotificationType.NEW_FOLLOWER, new Object[]{}, new Object[]{user.getName()});
    }

    public void unfollow(Long userId, Long following) {
        followerDao.deleteFollow(userId, following);
    }

}
