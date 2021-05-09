package com.vaibhav.sociofy.service.savedPost

import com.vaibhav.sociofy.models.SavedPost

interface SavedPostService {

    fun savePost(userId:Long, postId:Long):SavedPost

    fun deleteSavedPost(savedPostId:Long)

    fun deleteAllSavedPostsOfAUSer(userId: Long)

    fun getAllSavedPostsOfAUser(userId: Long):List<SavedPost>

    fun checkIfSavedPostExists(savedPostId: Long):Boolean

}