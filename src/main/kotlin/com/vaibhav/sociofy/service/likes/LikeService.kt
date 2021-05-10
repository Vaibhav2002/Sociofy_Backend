package com.vaibhav.sociofy.service.likes

interface LikeService {

    fun likePost(userId:Long, postId:Long)

    fun dislikePost(userId: Long, postId: Long)

    fun getAllLikedPostsIds(userId: Long):List<Long>

    fun getAllLikersOfAPost(postId: Long):List<Long>

    fun deleteAllOfAUser(userId: Long)
    fun deleteAll()

    fun isLikedByUser(userId: Long):Boolean

    fun getLikeCount(postId: Long):Long
}