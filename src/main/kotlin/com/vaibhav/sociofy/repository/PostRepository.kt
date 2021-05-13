package com.vaibhav.sociofy.repository

import com.vaibhav.sociofy.models.entities.Post
import com.vaibhav.sociofy.models.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {

    @Query("SELECT p FROM post_table p WHERE p.id IN ?1")
    fun getAllFeedPosts(users:List<User>): List<Post>

    fun findAllByUser(user:User): List<Post>

    fun deleteAllByUser(user: User)
}