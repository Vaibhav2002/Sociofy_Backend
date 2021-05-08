package com.vaibhav.sociofy.service.likes


import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
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
class LikeServiceImplTest {

    companion object{
        const val USER_ID = 1L
        const val POST_ID = 1L
    }

    @Autowired
    private lateinit var service: LikeService

    @Test
    fun likePost() {
        assertDoesNotThrow {
            service.likePost(USER_ID, POST_ID)
        }
    }

    @Test
    fun dislikePost() {
        assertDoesNotThrow {
            service.dislikePost(USER_ID, POST_ID)
        }
    }

    @Test
    fun getAllLikedPostsIdsWhenThereDoesNotExistAny() {
        val posts = service.getAllLikedPostsIds(USER_ID)
        assertThat(posts).isEmpty()
    }


    @Test
    fun getAllLikedPostsIdsWhenThereDoesExists() {
        service.likePost(USER_ID, POST_ID)
        service.likePost(USER_ID, 2)
        service.likePost(USER_ID, 3)
        service.likePost(USER_ID, 4)
        val posts = service.getAllLikedPostsIds(USER_ID)
        println(posts)
        assertThat(posts).isNotEmpty()
    }

    @Test
    fun getAllLikersOfAPostWhereThereAreNoLikers() {
        val likers = service.getAllLikersOfAPost(POST_ID)
        assertThat(likers).isEmpty()
    }

    @Test
    fun getAllLikersOfAPostWhereThereAreLikers() {
        service.likePost(USER_ID, POST_ID)
        service.likePost(2, POST_ID)
        service.likePost(3, POST_ID)
        service.likePost(4, POST_ID)
        val likers = service.getAllLikersOfAPost(POST_ID)
        println(likers)
        assertThat(likers).isNotEmpty()
    }
}