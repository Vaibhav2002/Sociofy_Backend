package com.vaibhav.sociofy.service.follower_following

interface FollowerFollowingService {

    fun getAllFollowers(userId: Long): List<Long>

    fun getAllFollowing(userId: Long): List<Long>

    fun followUser(followerId:Long, followingId:Long)

    fun unfollowUser(followerId:Long, followingId:Long)

    fun deleteAllOfUser(userId: Long)

    fun deleteAll()

    fun deleteAllFollowersOfAUser(userId: Long)

    fun deleteAllFollowingOfAUser(userId: Long)

}