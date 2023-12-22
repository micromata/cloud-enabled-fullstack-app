package com.oskarwiedeweg.cloudwork.profiles.follower;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class FollowerService {

    private final FollowerDao followerDao;

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
