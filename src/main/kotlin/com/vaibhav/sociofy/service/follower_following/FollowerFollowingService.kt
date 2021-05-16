package com.vaibhav.sociofy.service.follower_following

import com.vaibhav.sociofy.models.entities.User

interface FollowerFollowingService {

    fun getAllFollowers(userId: Long): List<Long>

    fun getAllFollowing(userId: Long): List<Long>

    fun followUser(follower: User, following: User)

    fun unfollowUser(follower: User, following: User)

    fun deleteAllOfUser(userId: Long)

    fun deleteAll()

    fun deleteAllFollowersOfAUser(userId: Long)

    fun deleteAllFollowingOfAUser(userId: Long)

}