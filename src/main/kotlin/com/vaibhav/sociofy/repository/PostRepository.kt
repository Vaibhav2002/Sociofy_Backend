package com.vaibhav.sociofy.repository

import com.vaibhav.sociofy.models.entities.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {

    @Query("SELECT p FROM post_table p WHERE p.userId IN ?1")
    fun getAllFeedPosts(userIds: List<Long>): List<Post>

    @Query("SELECT p FROM post_table p WHERE p.userId = ?1")
    fun getAllPostsOfUSer(userId: Long): List<Post>

    fun deleteAllByUserId(userId: Long)
}