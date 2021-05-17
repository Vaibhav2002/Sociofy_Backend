package com.vaibhav.sociofy.repository

import com.vaibhav.sociofy.models.entities.Follower_Following
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FollowerFollowingRepository : JpaRepository<Follower_Following, Long>{

    @Query("SELECT f.follower.userId FROM follower_following_table f WHERE f.following.userId = ?1")
    fun findAllFollowers(userId:Long):List<Long>

    @Query("SELECT f.following.userId FROM follower_following_table f WHERE f.follower.userId = ?1")
    fun findAllFollowing(userId: Long):List<Long>

    @Modifying
    @Query("DELETE FROM follower_following_table f WHERE f.follower.userId = ?1")
    fun deleteAllByFollowerId(userId: Long)

    @Modifying
    @Query("DELETE FROM follower_following_table f WHERE f.following.userId = ?1")
    fun deleteAllByFollowingId(userId: Long)

    @Modifying
    @Query("DELETE FROM follower_following_table f WHERE f.follower.userId = ?1 AND f.following.userId = ?2")
    fun unfollowUser(followerId:Long, followingId:Long)

    @Query("SELECT f FROM follower_following_table f WHERE f.follower.userId = ?1 AND f.following.userId = ?2")
    fun getFollowerFollowing(followerId: Long,followingId: Long): Optional<Follower_Following>
}