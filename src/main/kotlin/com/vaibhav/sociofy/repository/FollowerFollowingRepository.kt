package com.vaibhav.sociofy.repository

import com.vaibhav.sociofy.models.entities.Follower_Following
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FollowerFollowingRepository : JpaRepository<Follower_Following, Long>{

    @Query("SELECT f.follower.userId FROM follower_following_table f WHERE f.following.userId = ?1")
    fun findAllFollowers(userId:Long):List<Long>

    @Query("SELECT f.following.userId FROM follower_following_table f WHERE f.follower.userId = ?1")
    fun findAllFollowing(userId: Long):List<Long>

    @Query("DELETE FROM follower_following_table f WHERE f.follower.userId = ?1")
    fun deleteAllByFollowerId(userId: Long)

    @Query("DELETE FROM follower_following_table f WHERE f.following.userId = ?1")
    fun deleteAllByFollowingId(userId: Long)
}