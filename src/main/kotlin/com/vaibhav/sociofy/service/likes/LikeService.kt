package com.vaibhav.sociofy.service.likes

import com.vaibhav.sociofy.models.entities.Like
import com.vaibhav.sociofy.models.entities.Post
import com.vaibhav.sociofy.models.entities.User

interface LikeService {

    fun likePost(user: User, post:Post):Like

    fun dislikePost(userId: Long,postId: Long)

    fun getAllLikedPosts(userId:Long):List<Post>

    fun getAllLikersOfAPost(postId:Long):List<Long>

    fun getLike(userId: Long,postId: Long):Like


    fun exists(userId: Long,postId: Long):Boolean
    fun deleteAllOfAUser(userId:Long)

    fun deleteAll()

    fun deleteAllByPostId(postId:Long)

    fun isLikedByUser(userId:Long):Boolean

    fun getLikeCount(postId:Long):Long
}