package com.vaibhav.sociofy.repository

import com.vaibhav.sociofy.models.Like
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface LikeRepository : JpaRepository<Like, Long> {

    @Query("SELECT l.postId FROM like_table l WHERE l.userId = ?1")
    fun findAllPostIdsByUserId(userId: Long): List<Long>

    @Query("SELECT l.userId FROM like_table l WHERE l.postId = ?1")
    fun findAllLikersOfAPost(postId: Long): List<Long>


}