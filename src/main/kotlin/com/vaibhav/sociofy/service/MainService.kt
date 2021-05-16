package com.vaibhav.sociofy.service

import com.vaibhav.sociofy.exceptions.AuthException
import com.vaibhav.sociofy.exceptions.LikeException
import com.vaibhav.sociofy.exceptions.SavedPostException
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
import javax.transaction.Transactional


/**
 *This MainService class wraps all the other services and acts as a main service
 *layer which communicates with different services beneath it to prepare data for controller layer
 *
 *All methods in this class represents an endpoint.
 **/


@Service
@Transactional
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
        val followers = user.followers.filter {
            it.following.userId == user.userId
        }.map {
            it.follower.userId
        }
        val following = user.following.filter {
            it.follower.userId == user.userId
        }.map {
            it.following.userId
        }
        val posts = user.posts.map {
            getCompletePostResponseData(it)
        }
        return UserDetailsResponse(
            userId = user.userId,
            username = user.username,
            profile_image_url = user.profile_img_url,
            bio = user.bio,
            followers = followers,
            following = following,
            posts = posts
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
        } catch (e: AuthException) {
            Response.ErrorResponse(message = e.message)
        }
    }

    fun loginUser(email: String, password: String): Any {
        return try {
            val userData = authServiceImpl.loginUser(email, password)
            getCompleteUserDetails(userData)
        } catch (e: AuthException) {
            Response.ErrorResponse(message = e.message)
        }

    }

    fun deleteUser(userId: Long): Any {
        return try {
            authServiceImpl.deleteUser(userId)
            Response.SuccessResponse(message = "User and all its data deleted")
        } catch (e: AuthException) {
            Response.ErrorResponse(e.message)
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
        } catch (e: AuthException) {
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
            getCompleteUserDetails(user.get())
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
        val likeCount = post.likes.size.toLong()
        return PostResponse(
            username = post.user.username,
            user_profile_image = post.user.profile_img_url,
            likeCount = likeCount,
            likes = post.likes.map { it.user.userId },
            postId = post.postId,
            userId = post.user.userId,
            description = post.description,
            imageUrl = post.imageUrl,
            timeStamp = post.timeStamp
        )

    }

    fun uploadPost(userId: Long, description: String, image_url: String): Any {
        return try {
            val user = authServiceImpl.getUserById(userId)
            var post = Post(description, imageUrl = image_url, user = user)
            post = postServiceImpl.insertIntoDb(post)
            insertNotification(userId, post.postId)
            getCompletePostResponseData(post)
        } catch (e: Exception) {
            Response.ErrorResponse(e.message.toString())
        }

    }

    fun getAllPosts(): List<PostResponse> {
        val posts = postServiceImpl.getAllPosts()
        return posts.map {
            getCompletePostResponseData(it)
        }
    }

    fun getAllPostByUserIds(userIds: List<Long>): List<PostResponse> {
        val users = authServiceImpl.getUsersByUserIds(userIds)
        val posts = postServiceImpl.getAllFeedPosts(users)
        return posts.map {
            getCompletePostResponseData(it)
        }
    }


    fun getPostsOfUser(userId: Long): List<PostResponse> {
        val user = authServiceImpl.getUserById(userId)
        val posts = postServiceImpl.getPostsOfUser(user)
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
        val postResponse = getCompletePostResponseData(savedPost.post)
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

    fun deleteSavedPost(savedPostId: Long): Response {
        return try {
            savedPostServiceImpl.deleteSavedPost(savedPostId)
            Response.SuccessResponse(message = "SavedPost deleted successfully")
        } catch (e: SavedPostException) {
            Response.ErrorResponse(message = "Failed to delete saved post")
        }
    }

    fun deleteAllSavedPostsOfAUser(userId: Long): Response {
        return try {
            savedPostServiceImpl.deleteAllSavedPostsOfAUSer(userId)
            Response.SuccessResponse(message = "All SavedPost deleted successfully")
        } catch (e: SavedPostException) {
            Response.ErrorResponse(message = "Failed to delete all saved post")
        }
    }

    fun savePost(userId: Long, postId: Long): Any {
        return try {
            val user = authServiceImpl.getUserById(userId)
            val post = postServiceImpl.getPost(postId)
            val savedPost = savedPostServiceImpl.savePost(SavedPost(user = user, post = post))
            getCompleteSavedPostDetail(savedPost)
        } catch (e: SavedPostException) {
            Response.ErrorResponse(message = "Failed to delete saved post")
        }
    }

    /*
    Like Service
     */


    fun likePost(userId: Long, postId: Long): Response {
        return try {
            val user = authServiceImpl.getUserById(userId)
            val post = postServiceImpl.getPost(postId)
            likeServiceImpl.likePost(user, post)
            Response.SuccessResponse("Post liked successfully")
        } catch (e: LikeException) {
            Response.ErrorResponse(message = e.message)
        }
    }

    fun disLikePost(userId: Long, postId: Long): Response {
        return try {
            likeServiceImpl.dislikePost(userId,postId)
            Response.SuccessResponse("Post disliked successfully")
        } catch (e: LikeException) {
            Response.ErrorResponse(message = e.message)
        }
    }

    fun getAllLikedPostsOfAUser(userId: Long) = likeServiceImpl.getAllLikedPosts(userId).map {
        getCompletePostResponseData(it)
    }

    fun getAllLikersOfAPost(postId: Long) = likeServiceImpl.getAllLikersOfAPost(postId)

    fun deleteAllLikesOfAUser(userId: Long): Response {
        return try {
            likeServiceImpl.deleteAllOfAUser(userId)
            Response.SuccessResponse("All likes of this user deleted successfully")
        } catch (e: LikeException) {
            Response.ErrorResponse(message = e.message)
        }
    }

    fun deleteAllLikesOfAPost(postId: Long): Response {
        return try {
            likeServiceImpl.deleteAllByPostId(postId)
            Response.SuccessResponse("All likes of this post deleted successfully")
        } catch (e: LikeException) {
            Response.ErrorResponse(message = e.message)
        }
    }

    fun deleteAllLikes(): Response {
        return try {
            likeServiceImpl.deleteAll()
            Response.SuccessResponse("All likes deleted successfully")
        } catch (e: LikeException) {
            Response.ErrorResponse(message = e.message)
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
            val follower = authServiceImpl.getUserById(followerUserId)
            val following =authServiceImpl.getUserById(followingUserId)
            followFollowingServiceImpl.followUser(follower, following)
            Response.SuccessResponse("User followed Successfully")
        } catch (e: Exception) {
            Response.ErrorResponse(message = "Failed to follow user")
        }
    }

    fun unfollowUser(followerUserId: Long, followingUserId: Long): Response {
        return try {
            val follower = authServiceImpl.getUserById(followerUserId)
            val following =authServiceImpl.getUserById(followingUserId)
            followFollowingServiceImpl.unfollowUser(follower, following)
            Response.SuccessResponse("User unfollowed Successfully")
        } catch (e: Exception) {
            Response.ErrorResponse(message = "Failed to unfollow user")
        }
    }

    fun getAllFollowersOfUser(userId: Long) = followFollowingServiceImpl.getAllFollowers(userId)

    fun getAllFollowingOfAUser(userId: Long) = followFollowingServiceImpl.getAllFollowing(userId)

}