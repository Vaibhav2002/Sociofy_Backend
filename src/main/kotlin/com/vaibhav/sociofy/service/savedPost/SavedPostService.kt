package com.vaibhav.sociofy.service.savedPost

import com.vaibhav.sociofy.models.entities.SavedPost

interface SavedPostService {

    fun savePost(savedPost: SavedPost): SavedPost

    fun deleteSavedPost(savedPostId:Long)

    fun deleteAllSavedPostsOfAUSer(userId: Long)

    fun deleteAllByPostId(postId: Long)

    fun deleteAllSavedPosts()

    fun getAllSavedPostsOfAUser(userId: Long):List<SavedPost>

    fun checkIfSavedPostExists(savedPostId: Long):Boolean

}