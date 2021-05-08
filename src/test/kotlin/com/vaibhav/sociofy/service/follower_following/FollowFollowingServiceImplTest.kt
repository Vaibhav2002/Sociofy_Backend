package com.vaibhav.sociofy.service.follower_following

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.assertDoesNotThrow
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

    companion object{
        const val iD1 = 1L
        const val iD2 = 2L
    }

    @Autowired
    private lateinit var service:FollowFollowingServiceImpl

    @Test
    fun getAllFollowersWhenThereAreNoFollowers() {
        val followers = service.getAllFollowers(iD2)
        println(followers)
        assertThat(followers).isEmpty()
    }

    @Test
    fun getAllFollowersWhenThereAreFollowers() {
        service.followUser(iD1, iD2)
        service.followUser(3, iD2)
        service.followUser(5, iD1)
        val followers = service.getAllFollowers(iD2)
        println(followers)
        assertThat(followers).isNotEmpty()
    }

    @Test
    fun getAllFollowingWhenThereAreFollowing() {
        val following = service.getAllFollowing(iD1)
        println(following)
        assertThat(following).isEmpty()
    }

    @Test
    fun getAllFollowingWhenThereAreNoFollowing() {

        service.followUser(iD1, iD2)
        service.followUser(3, iD1)
        service.followUser(iD1,5)
        val following = service.getAllFollowing(iD1)
        println(following)
        assertThat(following).isNotEmpty()
    }

    @Test
    fun followUser() {
        assertDoesNotThrow {
            service.followUser(iD1, iD2)
        }
    }

    @Test
    fun unfollowUser() {
        assertDoesNotThrow {
            service.unfollowUser(iD1, iD2)
        }
    }
}