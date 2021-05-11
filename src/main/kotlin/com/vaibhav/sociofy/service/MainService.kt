package com.vaibhav.sociofy.service

import com.vaibhav.sociofy.models.entities.Notification
import com.vaibhav.sociofy.models.entities.Post
import com.vaibhav.sociofy.models.entities.SavedPost
import com.vaibhav.sociofy.models.entities.User
import com.vaibhav.sociofy.models.response.*
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

    private fun getCompleteUserDetails(user: User): UserDetailsResponse {
        val followers = followFollowingServiceImpl.getAllFollowers(user.userId)
        val following = followFollowingServiceImpl.getAllFollowing(user.userId)
        return UserDetailsResponse(
            userId = user.userId,
            username = user.username,
            profile_image_url = user.profile_img_url,
            bio = user.bio,
            followers = followers,
            following = following
        )
    }

    private fun getMinimalUserDetails(user: User): UserResponse {
        return UserResponse(
            userId = user.userId,
            username = user.username,
            profileImgUrl = user.profile_img_url
        )
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
            Response.ErrorResponse(e.message.toString())
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

    fun updateUserDetails(userId: Long, username: String, bio: String, profileImageUrl: String): Any {
        return try {
            val user = authServiceImpl.updateUserDetails(userId, username, bio, profileImageUrl)
            getCompleteUserDetails(user)
        } catch (e: Exception) {
            Response.ErrorResponse(message = e.message.toString())
        }

    }

    fun getUserDetailByUserId(userId: Long): Any {
        return try {
            val user = authServiceImpl.getUserById(userId)
            getCompleteUserDetails(user)
        } catch (e: Exception) {
            Response.ErrorResponse(message = e.message.toString())
        }
    }

    fun getUsersByUserIds(userIds: List<Long>): List<UserResponse> {
        val users = authServiceImpl.getUsersByUserIds(userIds)
        return users.map {
            getMinimalUserDetails(it)
        }
    }


    fun getUserDetailsByEmail(email: String): Any {
        return try {
            val user = authServiceImpl.getUserByEmail(email)
            getCompleteUserDetails(user)
        } catch (e: Exception) {
            Response.ErrorResponse(message = e.message.toString())
        }
    }

    fun getAllUsers(): List<UserResponse> {
        val users = authServiceImpl.getAllUsers()
        return users.map {
            getMinimalUserDetails(it)
        }

    }


    /*
    Post Service
     */


    private fun getCompletePostResponseData(post: Post): PostResponse {
        val user = authServiceImpl.getUserById(post.userId)
        val likeCount = likeServiceImpl.getLikeCount(post.postId)
        val likedByMe = likeServiceImpl.isLikedByUser(post.userId)
        return PostResponse(
            username = user.username,
            user_profile_image = user.profile_img_url,
            likeCount = likeCount,
            likedByMe = likedByMe,
            postId = post.postId,
            userId = user.userId,
            description = post.description,
            imageUrl = post.imageUrl,
            timeStamp = post.timeStamp
        )

    }

    fun savePost(userId: Long, description: String, image_url: String): PostResponse {
        var post = Post(userId, description, imageUrl = image_url)
        post = postServiceImpl.insertIntoDb(post)
        insertNotification(userId, post.postId)
        return getCompletePostResponseData(post)
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

    private fun getCompleteSavedPostDetail(savedPost: SavedPost): SavedPostResponse {
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


    fun likePost(userId: Long, postId: Long): Response {
        return try {
            likeServiceImpl.likePost(userId, postId)
            Response.SuccessResponse("Post liked successfully")
        } catch (e: Exception) {
            Response.ErrorResponse(message = "Failed to like post")
        }
    }

    fun disLikePost(userId: Long, postId: Long): Response {
        return try {
            likeServiceImpl.dislikePost(userId, postId)
            Response.SuccessResponse("Post disliked successfully")
        } catch (e: Exception) {
            Response.ErrorResponse(message = "Failed to dislike post")
        }
    }

    fun getAllLikedPostIdsOfAUser(userId: Long) = likeServiceImpl.getAllLikedPostsIds(userId)

    fun getAllLikersOfAPost(postId: Long) = likeServiceImpl.getAllLikersOfAPost(postId)

    fun deleteAllLikesOfAUser(userId: Long): Response {
        return try {
            likeServiceImpl.deleteAllOfAUser(userId)
            Response.SuccessResponse("All likes of this user deleted successfully")
        } catch (e: Exception) {
            Response.ErrorResponse(message = "Failed to delete likes of this user")
        }
    }

    fun deleteAllLikes(): Response {
        return try {
            likeServiceImpl.deleteAll()
            Response.SuccessResponse("All likes deleted successfully")
        } catch (e: Exception) {
            Response.ErrorResponse(message = "Failed to delete all likes")
        }
    }
    /*
    Notification Service
     */

    private fun getCompleteNotificationDetails(notification: Notification): NotificationResponse {
        val user = authServiceImpl.getUserById(notification.userId)
        val post = postServiceImpl.getPost(notification.postId)
        return NotificationResponse(
            userId = user.userId,
            postId = post.postId,
            notificationId = notification.notificationId,
            username = user.username,
            postImageUrl = post.imageUrl,
            timeStamp = notification.timeStamp
        )
    }

    fun insertNotification(userId: Long, postId: Long) =
        notificationServiceImpl.insertNotification(userId, postId)

    fun getAllNotificationByUserIds(userIds: List<Long>) =
        notificationServiceImpl.getAllNotificationByUserIds(userIds).map {
            getCompleteNotificationDetails(it)
        }

    fun deleteNotificationByPostId(postId: Long): Response {
        return try {
            notificationServiceImpl.deleteNotificationByPostId(postId)
            Response.SuccessResponse(message = "Notification deleted successfully")
        } catch (e: Exception) {
            Response.ErrorResponse(message = "Failed to delete notification")
        }

    }

    fun deleteAllNotificationsByUserId(userId: Long): Response {
        return try {
            notificationServiceImpl.deleteAllNotificationsByUserId(userId)
            Response.SuccessResponse(message = "All Notification of this user deleted successfully")
        } catch (e: Exception) {
            Response.ErrorResponse(message = "Failed to delete all notification of this user")
        }
    }

    fun deleteAllNotifications(): Response {
        return try {
            notificationServiceImpl.deleteAllNotifications()
            Response.SuccessResponse(message = "All Notifications deleted successfully")
        } catch (e: Exception) {
            Response.ErrorResponse(message = "Failed to delete all notification")
        }
    }

    /*
    Follow_Following Service
     */

    fun followUser(followerUserId: Long, followingUserId: Long): Response {
        return try {
            followFollowingServiceImpl.followUser(followerUserId, followingUserId)
            Response.SuccessResponse("User followed Successfully")
        } catch (e: Exception) {
            Response.ErrorResponse(message = "Failed to follow user")
        }
    }

    fun unfollowUser(followerUserId: Long, followingUserId: Long): Response {
        return try {
            followFollowingServiceImpl.unfollowUser(followerUserId, followingUserId)
            Response.SuccessResponse("User unfollowed Successfully")
        } catch (e: Exception) {
            Response.ErrorResponse(message = "Failed to unfollow user")
        }
    }

    fun getAllFollowersOfUser(userId: Long) = followFollowingServiceImpl.getAllFollowers(userId)

    fun getAllFollowingOfAUser(userId: Long) = followFollowingServiceImpl.getAllFollowing(userId)

}