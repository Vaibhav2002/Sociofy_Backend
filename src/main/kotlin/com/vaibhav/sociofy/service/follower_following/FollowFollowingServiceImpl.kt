package com.vaibhav.sociofy.service.follower_following

import com.vaibhav.sociofy.models.Follower_Following
import com.vaibhav.sociofy.repository.FollowerFollowingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FollowFollowingServiceImpl @Autowired constructor(
    private val repository: FollowerFollowingRepository
) : FollowerFollowingService {


    override fun getAllFollowers(userId: Long): List<Long> = repository.findAllFollowers(userId)

    override fun getAllFollowing(userId: Long): List<Long> = repository.findAllFollowing(userId)

    override fun followUser(followerId: Long, followingId: Long) {
        repository.save(Follower_Following(followerId, followingId))
    }

    override fun unfollowUser(followerId: Long, followingId: Long) {
        repository.delete(Follower_Following(followerId,followingId))
    }
}