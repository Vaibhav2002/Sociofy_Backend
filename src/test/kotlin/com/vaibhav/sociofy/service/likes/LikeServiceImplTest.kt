package com.vaibhav.sociofy.service.likes


import com.google.common.truth.Truth.assertThat
import com.vaibhav.sociofy.models.entities.Post
import com.vaibhav.sociofy.models.entities.User
import com.vaibhav.sociofy.service.auth.AuthServiceImpl
import com.vaibhav.sociofy.service.post.PostServiceImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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

    @Autowired
    private lateinit var service: LikeService

    @Autowired
    private lateinit var authService: AuthServiceImpl

    @Autowired
    private lateinit var postService: PostServiceImpl


    //users
    private lateinit var user1: User
    private lateinit var user2: User
    private lateinit var user3: User

    //posts
    private lateinit var post1: Post
    private lateinit var post2: Post
    private lateinit var post3: Post

    @BeforeEach
    fun insertUsersAndPosts() {
        user1 = authService.registerUser("hello", "lmao", "lmao")
        user2 = authService.registerUser("hello", "nigga", "lmao")
        user3 = authService.registerUser("hello", "lol", "lmao")
        post1 = postService.insertIntoDb(Post(user = user1))
        post2 = postService.insertIntoDb(Post(user = user2))
        post3 = postService.insertIntoDb(Post(user = user3))
    }

    @AfterEach
    fun deleteAll() {
        authService.deleteAllUsers()
        postService.deleteAllPosts()
    }


    @Test
    fun likePost() {
        val like = service.likePost(user1, post3)
        assertThat(service.exists(like)).isTrue()

    }

    @Test
    fun dislikePost() {
        val like = service.likePost(user1,post3)
        service.dislikePost(like)
        assertThat(service.exists(like)).isFalse()
    }

    @Test
    fun getAllLikedPostsIdsWhenThereDoesNotExistAny() {
        val posts = service.getAllLikedPosts(user1.userId)
        assertThat(posts).isEmpty()
    }


    @Test
    fun getAllLikedPostsIdsWhenThereDoesExists() {
        service.likePost(user1, post2)
        service.likePost(user1, post1)
        service.likePost(user1, post3)
        val posts5 = service.getAllLikedPosts(user1.userId)
        assertThat(posts5).isNotEmpty()
    }

    @Test
    fun getAllLikersOfAPostWhereThereAreNoLikers() {
        val likers = service.getAllLikersOfAPost(post1.postId)
        assertThat(likers).isEmpty()
    }

    @Test
    fun getAllLikersOfAPostWhereThereAreLikers() {
        service.likePost(user1, post2)
        service.likePost(user2, post2)
        service.likePost(user3, post2)
        val likers = service.getAllLikersOfAPost(post2.postId)
        println(likers)
        assertThat(likers).isNotEmpty()
    }
}