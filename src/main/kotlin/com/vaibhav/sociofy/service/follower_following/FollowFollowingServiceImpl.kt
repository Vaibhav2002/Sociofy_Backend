package com.vaibhav.sociofy.service.follower_following

import com.vaibhav.sociofy.models.entities.Follower_Following
import com.vaibhav.sociofy.models.entities.User
import com.vaibhav.sociofy.repository.FollowerFollowingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FollowFollowingServiceImpl @Autowired constructor(
    private val repository: FollowerFollowingRepository
) : FollowerFollowingService {


    override fun getAllFollowers(userId: Long): List<Long> = repository.findAllFollowers(userId)

    override fun deleteAllOfUser(userId: Long) {
        deleteAllFollowersOfAUser(userId)
        deleteAllFollowingOfAUser(userId)
    }

    override fun deleteAll() = repository.deleteAll()

    override fun deleteAllFollowersOfAUser(userId: Long) =
        repository.deleteAllByFollowingId(userId)


    override fun deleteAllFollowingOfAUser(userId: Long) =
        repository.deleteAllByFollowerId(userId)


    override fun getAllFollowing(userId: Long): List<Long> = repository.findAllFollowing(userId)

    override fun followUser(follower: User, following: User) {
        repository.save(Follower_Following(follower, following))
    }

    override fun unfollowUser(follower: User, following: User) {
        repository.delete(Follower_Following(follower, following))
    }
}