package com.vaibhav.sociofy.repository

import com.vaibhav.sociofy.models.entities.Like
import com.vaibhav.sociofy.models.entities.Post
import com.vaibhav.sociofy.models.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LikeRepository : JpaRepository<Like, Long> {

    @Query("SELECT l.post FROM like_table l WHERE l.user.userId = ?1")
    fun findAllLikedPostIdsByUserId(userId: Long): List<Post>

    @Query("SELECT l.user.userId FROM like_table l WHERE l.post.postId = ?1")
    fun findAllLikersOfAPost(postId: Long): List<Long>

    @Modifying
    @Query("DELETE FROM like_table l WHERE l.user.userId = ?1")
    fun deleteAllByUserId(userId: Long)

    @Modifying
    @Query("DELETE FROM like_table l WHERE l.post.postId = ?1")
    fun deleteAllByPostId(postId: Long)

    fun existsByUser(user: User):Boolean

    @Query("SELECT COUNT(l.user) FROM like_table l WHERE l.post.postId = ?1")
    fun getLikeCount(postId: Long):Long

    @Query("SELECT l FROM like_table l WHERE l.user.userId = ?1 AND l.post.postId = ?2")
    fun getLike(userId: Long,postId: Long):Optional<Like>


    @Modifying
    @Query("DELETE FROM like_table l WHERE l.user.userId = ?1 AND l.post.postId = ?2")
    fun disLike(userId: Long,postId: Long)


}