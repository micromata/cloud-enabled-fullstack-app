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

    public void follow(Long userId, Long following) {
        followerDao.createFollow(userId, following);
    }

    public void unfollow(Long userId, Long following) {
        followerDao.deleteFollow(userId, following);
    }

}
