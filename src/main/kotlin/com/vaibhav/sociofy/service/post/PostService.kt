package com.vaibhav.sociofy.service.post

import com.vaibhav.sociofy.models.entities.Post

interface PostService {
    //uploading
    fun insertIntoDb(post: Post): Post

    //fetching
    fun getAllPosts():List<Post>

    fun getAllFeedPosts(userIds:List<Long>):List<Post>

    fun getPostsOfUser(userId:Long):List<Post>

    fun getAllPostsByIds(postIds:List<Long>):List<Post>

    fun getPost(postId: Long): Post

    //querying
    fun checkIfPostExists(postId: Long):Boolean

    //deleting
    fun deletePost(postId:Long)
    fun deleteAllPosts()
    fun deleteAllPostsOfAUser(userId: Long)
}