package com.vaibhav.sociofy.repository

import com.vaibhav.sociofy.models.Post
import com.vaibhav.sociofy.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {

    @Query("SELECT p FROM post_table p WHERE p.userId IN ?1")
    fun getAllFeedPosts(userIds: List<Long>): List<Post>

    @Query("SELECT p FROM post_table p WHERE p.userId = ?1")
    fun getAllPostsOfUSer(userId: Long): List<Post>
}