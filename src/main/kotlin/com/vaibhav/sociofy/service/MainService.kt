package com.vaibhav.sociofy.service

import com.vaibhav.sociofy.models.entities.Post
import com.vaibhav.sociofy.models.entities.SavedPost
import com.vaibhav.sociofy.models.entities.User
import com.vaibhav.sociofy.models.response.PostResponse
import com.vaibhav.sociofy.models.response.Response
import com.vaibhav.sociofy.models.response.SavedPostResponse
import com.vaibhav.sociofy.models.response.UserDetailsResponse
import com.vaibhav.sociofy.service.auth.AuthServiceImpl
import com.vaibhav.sociofy.service.follower_following.FollowFollowingServiceImpl
import com.vaibhav.sociofy.service.likes.LikeServiceImpl
import com.vaibhav.sociofy.service.notification.NotificationServiceImpl
import com.vaibhav.sociofy.service.post.PostServiceImpl
import com.vaibhav.sociofy.service.savedPost.SavedPostServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


/**
 *This MainService class wraps all the other services and acts as a main service
 *layer which communicates with different services beneath it to prepare data for controller layer
 *
 *All methods in this class represents an endpoint.
 **/


@Service
class MainService @Autowired constructor(
    private val authServiceImpl: AuthServiceImpl,
    private val postServiceImpl: PostServiceImpl,
    private val likeServiceImpl: LikeServiceImpl,
    private val followFollowingServiceImpl: FollowFollowingServiceImpl,
    private val notificationServiceImpl: NotificationServiceImpl,
    private val savedPostServiceImpl: SavedPostServiceImpl
) {

    /*
    Auth Service
     */

    fun getCompleteUserDetails(user: User): UserDetailsResponse {
        val followers = followFollowingServiceImpl.getAllFollowers(user.userId)
        val following = followFollowingServiceImpl.getAllFollowing(user.userId)
        return UserDetailsResponse(userData = user, followers = followers, following = following)
    }

    fun registerUser(username: String, email: String, password: String): Any {
        return try {
            val userData = authServiceImpl.registerUser(username, email, password)
            getCompleteUserDetails(userData)
        } catch (e: Exception) {
            Response.ErrorResponse(message = e.message.toString())
        }
    }

    fun loginUser(email: String, password: String): Any {
        return try {
            val userData = authServiceImpl.loginUser(email, password)
            getCompleteUserDetails(userData)
        } catch (e: Exception) {
            Response.ErrorResponse(message = e.message.toString())
        }

    }

    fun deleteUser(userId: Long): Any {
        return try {
            authServiceImpl.deleteUser(userId)
            postServiceImpl.deleteAllPostsOfAUser(userId)
            savedPostServiceImpl.deleteAllSavedPostsOfAUSer(userId)
            notificationServiceImpl.deleteAllNotificationsByUserId(userId)
            likeServiceImpl.deleteAllOfAUser(userId)
            followFollowingServiceImpl.deleteAllOfUser(userId)
            Response.SuccessResponse(message = "User and all its data deleted")
        } catch (e: Exception) {
            Response.ErrorResponse(message = "Failed to delete user and all its data")
        }
    }

    fun deleteAllUsers(): Any {
        return try {
            authServiceImpl.deleteAllUsers()
            postServiceImpl.deleteAllPosts()
            savedPostServiceImpl.deleteAllSavedPosts()
            notificationServiceImpl.deleteAllNotifications()
            likeServiceImpl.deleteAll()
            followFollowingServiceImpl.deleteAll()
            Response.SuccessResponse(message = "All users and all their data deleted")
        } catch (e: Exception) {
            Response.ErrorResponse(message = "Failed to delete all users and all their data")
        }
    }

    fun updateUserDetails(userId: Long, username: String, bio: String): Any {
        return try {
            val user = authServiceImpl.updateUserDetails(userId, username, bio)
            getCompleteUserDetails(user)
        } catch (e: Exception) {
            Response.ErrorResponse(message = e.message.toString())
        }

    }

    fun getUserByUserId(userId: Long): Any {
        return try {
            val user = authServiceImpl.getUserById(userId)
            getCompleteUserDetails(user)
        } catch (e: Exception) {
            Response.ErrorResponse(message = e.message.toString())
        }
    }

    fun getUserByEmail(email: String): Any {
        return try {
            val user = authServiceImpl.getUserByEmail(email)
            getCompleteUserDetails(user)
        } catch (e: Exception) {
            Response.ErrorResponse(message = e.message.toString())
        }
    }

    fun getAllUsers() = authServiceImpl.getAllUsers()


    /*
    Post Service
     */


    fun getCompletePostResponseData(post: Post): PostResponse {
        val user = authServiceImpl.getUserById(post.userId)
        val likeCount = likeServiceImpl.getLikeCount(post.postId)
        val likedByMe = likeServiceImpl.isLikedByUser(post.userId)
        return PostResponse(
            postData = post,
            username = user.username,
            user_profile_image = user.profile_img_url,
            likeCount = likeCount,
            likedByMe = likedByMe
        )

    }

    fun getAllPosts(): List<PostResponse> {
        val posts = postServiceImpl.getAllPosts()
        return posts.map {
            getCompletePostResponseData(it)
        }
    }

    fun getAllPostByUserIds(userIds: List<Long>): List<PostResponse> {
        val posts = postServiceImpl.getAllFeedPosts(userIds)
        return posts.map {
            getCompletePostResponseData(it)
        }
    }

    fun getPostsOfUser(userId: Long): List<PostResponse> {
        val posts = postServiceImpl.getPostsOfUser(userId)
        return posts.map {
            getCompletePostResponseData(it)
        }
    }

    fun getPostDetail(postId: Long): Any {
        return try {
            val post = postServiceImpl.getPost(postId)
            getCompletePostResponseData(post)
        } catch (e: Exception) {
            Response.ErrorResponse(message = e.message.toString())
        }
    }

    fun deletePost(postId: Long): Any {
        return try {
            postServiceImpl.deletePost(postId)
            Response.SuccessResponse(message = "Post deleted successfully")
        } catch (e: Exception) {
            Response.ErrorResponse(message = "Failed to delete post")
        }
    }


    /*
    SavedPost Service
     */

    fun getCompleteSavedPostDetail(savedPost: SavedPost): SavedPostResponse {
        val post = postServiceImpl.getPost(savedPost.postId)
        val postResponse = getCompletePostResponseData(post)
        return SavedPostResponse(
            savedPostData = postResponse,
            saveId = savedPost.saveId,
            saveTimeStamp = savedPost.timeStamp
        )
    }

    fun getSavedPostsOfUser(userId: Long): List<SavedPostResponse> {
        val savedPost = savedPostServiceImpl.getAllSavedPostsOfAUser(userId)
        return savedPost.map {
            getCompleteSavedPostDetail(it)
        }
    }

    fun deleteSavedPost(savedPostId: Long): Any {
        return try {
            savedPostServiceImpl.deleteSavedPost(savedPostId)
            Response.SuccessResponse(message = "SavedPost deleted successfully")
        } catch (e: Exception) {
            Response.ErrorResponse(message = "Failed to delete saved post")
        }
    }

    fun deleteAllSavedPostsOfAUser(userId: Long): Any {
        return try {
            savedPostServiceImpl.deleteAllSavedPostsOfAUSer(userId)
            Response.SuccessResponse(message = "All SavedPost deleted successfully")
        } catch (e: Exception) {
            Response.ErrorResponse(message = "Failed to delete all saved post")
        }
    }

    fun savePost(userId: Long, postId: Long): Any {
        return try {
            val savedPost = savedPostServiceImpl.savePost(userId, postId)
            getCompleteSavedPostDetail(savedPost)
        } catch (e: Exception) {
            Response.ErrorResponse(message = "Failed to delete saved post")
        }
    }

    /*
    Like Service
     */

    /*
    Notification Service
     */

    /*
    Follow_Following Service
     */
}