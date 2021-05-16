package com.vaibhav.sociofy.service

import com.google.common.truth.Truth.assertThat
import com.vaibhav.sociofy.exceptions.AuthException
import com.vaibhav.sociofy.models.entities.Post
import com.vaibhav.sociofy.models.entities.User
import com.vaibhav.sociofy.service.auth.AuthServiceImpl
import com.vaibhav.sociofy.service.post.PostServiceImpl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.ANY
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = ANY)
class MainServiceTest {
    companion object{
        const val USER_ID = 1L
        const val EMAIL = "vaibhav_test_jaiswal@yahoo.com"
        const val PASSWORD = "vaibhav434424"
        const val USERNAME = "Vaibhav02"
        const val BIO = "Badass coder"

        const val POST_ID = 1L
        const val description = "Oh hell yeah"
    }

    @Autowired
    private lateinit var authServiceImpl:AuthServiceImpl
    @Autowired
    private lateinit var postServiceImpl: PostServiceImpl


    @Test
    fun registerUser() {
    }

    @Test
    fun loginUser() {
    }

    @Test
    fun deleteUser() {
    }

    @Test
    fun deleteAllUsers() {
    }

    @Test
    fun updateUserDetails() {
    }

    @Test
    fun getUserDetailByUserId() {
    }

    @Test
    fun getUsersByUserIds() {
    }

    @Test
    fun getUserDetailsByEmail() {
    }

    @Test
    fun getAllUsers() {
    }

    @Test
    fun savePost() {
    }

    @Test
    fun getAllPosts() {
    }

    @Test
    fun getAllPostByUserIds() {
    }

    @Test
    fun getPostsOfUser() {
        val user = authServiceImpl.registerUser(USERNAME, EMAIL, PASSWORD)
        println(user)
        val post = postServiceImpl.insertIntoDb(Post(user = user))
        println(post)
        postServiceImpl.insertIntoDb(Post(user = user))
        postServiceImpl.insertIntoDb(Post(user = user))
        val posts = postServiceImpl.getPostsOfUser(user)
        assertThat(posts).isNotEmpty()
    }

    @Test
    fun getPostDetail() {
    }

    @Test
    fun deletePost() {
    }

    @Test
    fun getSavedPostsOfUser() {
    }

    @Test
    fun deleteSavedPost() {
    }

    @Test
    fun deleteAllSavedPostsOfAUser() {
    }

    @Test
    fun testSavePost() {
    }

    @Test
    fun likePost() {
    }

    @Test
    fun disLikePost() {
    }

    @Test
    fun getAllLikedPostIdsOfAUser() {
    }

    @Test
    fun getAllLikersOfAPost() {
    }

    @Test
    fun deleteAllLikesOfAUser() {
    }

    @Test
    fun deleteAllLikes() {
    }

    @Test
    fun insertNotification() {
    }

    @Test
    fun getAllNotificationByUserIds() {
    }

    @Test
    fun deleteNotificationByPostId() {
    }

    @Test
    fun deleteAllNotificationsByUserId() {
    }

    @Test
    fun deleteAllNotifications() {
    }

    @Test
    fun followUser() {
    }

    @Test
    fun unfollowUser() {
    }

    @Test
    fun getAllFollowersOfUser() {
    }

    @Test
    fun getAllFollowingOfAUser() {
    }
}