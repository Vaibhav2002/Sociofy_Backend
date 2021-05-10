package com.vaibhav.sociofy.repository

import com.vaibhav.sociofy.models.entities.Follower_Following
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FollowerFollowingRepository : JpaRepository<Follower_Following, Long>{

    @Query("SELECT f.follower FROM follower_following_table f WHERE f.following = ?1")
    fun findAllFollowers(userId:Long):List<Long>

    @Query("SELECT f.following FROM follower_following_table f WHERE f.follower = ?1")
    fun findAllFollowing(userId: Long):List<Long>

    @Query("DELETE f FROM follower_following_table f WHERE f.follower = ?1 OR f.following = ?1")
    fun deleteAllOfAUser(userId: Long)
}