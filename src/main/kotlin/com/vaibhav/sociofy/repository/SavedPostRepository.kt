package com.vaibhav.sociofy.repository

import com.vaibhav.sociofy.models.entities.Post
import com.vaibhav.sociofy.models.entities.SavedPost
import com.vaibhav.sociofy.models.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface SavedPostRepository : JpaRepository<SavedPost, Long> {

    @Modifying
    @Query("DELETE FROM saved_post_table s WHERE s.user.userId = ?1")
    fun deleteAllByUser(userId: Long)

    @Modifying
    @Query("DELETE FROM saved_post_table s WHERE s.post.postId = ?1")
    fun deleteAllByPost(postId:Long)

    @Query("SELECT s FROM saved_post_table s WHERE s.user.userId = ?1")
    fun findAllByUser(userId: Long): List<SavedPost>
}