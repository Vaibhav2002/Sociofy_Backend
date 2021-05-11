package com.vaibhav.sociofy.repository

import com.vaibhav.sociofy.models.entities.Follower_Following
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FollowerFollowingRepository : JpaRepository<Follower_Following, Long>{

    @Query("SELECT f.followerId FROM follower_following_table f WHERE f.followingId = ?1")
    fun findAllFollowers(userId:Long):List<Long>

    @Query("SELECT f.followingId FROM follower_following_table f WHERE f.followerId = ?1")
    fun findAllFollowing(userId: Long):List<Long>

    fun deleteAllByFollowerId(userId: Long)

    fun deleteAllByFollowingId(userId: Long)
}