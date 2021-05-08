package com.vaibhav.sociofy.service.post

import com.vaibhav.sociofy.models.Post

interface PostService {
    //uploading
    fun insertIntoDb(post:Post):Post

    //fetching
    fun getAllPosts():List<Post>

    fun getAllFeedPosts(userIds:List<Long>):List<Post>

    fun getPostsOfUser(userId:Long):List<Post>

    fun getAllPostsByIds(postIds:List<Long>):List<Post>

    //querying
    fun checkIfPostExists(postId: Long):Boolean

    //deleting
    fun deletePost(postId:Long)
    fun deleteAllPosts()
}