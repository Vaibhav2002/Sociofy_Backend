package com.vaibhav.sociofy.service.follower_following

import com.google.common.truth.Truth.assertThat
import com.vaibhav.sociofy.exceptions.FollowerFollowingException
import com.vaibhav.sociofy.models.entities.User
import com.vaibhav.sociofy.service.auth.AuthService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class FollowFollowingServiceImplTest {


    @Autowired
    private lateinit var service: FollowFollowingServiceImpl

    @Autowired
    private lateinit var authService: AuthService

    private lateinit var user1: User
    private lateinit var user2: User
    private lateinit var user3: User


    @BeforeEach
    fun insertAllUsers() {
        user1 = authService.registerUser("", "hello", "wtaf")
        user2 = authService.registerUser("", "helloNigga", "wtaf")
        user3 = authService.registerUser("", "helloBomt", "wtaf")
    }

    @Test
    fun getAllFollowersWhenThereAreNoFollowers() {
        val followers = service.getAllFollowers(user1.userId)
        println(followers)
        assertThat(followers).isEmpty()
    }

    @Test
    fun getAllFollowersWhenThereAreFollowers() {
        service.followUser(user1, user2)
        service.followUser(user3, user1)
        service.followUser(user2, user1)
        val followers = service.getAllFollowers(user1.userId)
        println(followers)
        assertThat(followers).isNotEmpty()
    }

    @Test
    fun getAllFollowingWhenThereAreFollowing() {
        val following = service.getAllFollowing(user1.userId)
        println(following)
        assertThat(following).isEmpty()
    }

    @Test
    fun getAllFollowingWhenThereAreNoFollowing() {
        service.followUser(user1, user2)
        service.followUser(user3, user1)
        service.followUser(user3, user2)
        val following = service.getAllFollowing(user3.userId)
        println(following)
        assertThat(following).isNotEmpty()
    }

    @Test
    fun followUser() {
        assertDoesNotThrow {
            service.followUser(user1, user2)
        }
    }
    @Test
    fun followUserWhenAlreadyFollowing() {
        service.followUser(user1,user2)
        val exception = assertThrows<FollowerFollowingException> {
            service.followUser(user1, user2)
        }
        assertThat(exception.message).isEqualTo("Already following user")
    }


    @Test
    fun unfollowUserWhenNotFollowing() {
        val exception = assertThrows<FollowerFollowingException> {
            service.unfollowUser(user1.userId, user2.userId)
        }
        assertThat(exception.message).isEqualTo("Not following user")
    }
    @Test
    fun unfollowUserWhenFollowing() {
        service.followUser(user1,user2)
        assertDoesNotThrow {
            service.unfollowUser(user1.userId, user2.userId)
        }
    }
}