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

}
