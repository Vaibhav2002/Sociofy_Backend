package com.vaibhav.sociofy.service.post

import com.google.common.truth.Truth.assertThat
import com.vaibhav.sociofy.exceptions.PostException
import com.vaibhav.sociofy.models.entities.Post
import com.vaibhav.sociofy.models.entities.User
import com.vaibhav.sociofy.service.auth.AuthServiceImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
class PostServiceImplTest {

    companion object {

        private const val USER_ID = 1L
        private const val POST_DESCRIPTION = "Oh hell yeah"
        private const val POST_IMAGE_URL = "hello"
        const val POST_DOES_NOT_EXIST_MESSAGE = "Post does not exist"
        val post = Post(
            description = POST_DESCRIPTION,
            imageUrl = POST_IMAGE_URL,
        )
    }

    @Autowired
    private lateinit var postService: PostServiceImpl

    @Autowired
    private lateinit var authService: AuthServiceImpl

    private lateinit var user: User
    private lateinit var user2: User
    private lateinit var user3: User

    @BeforeEach
    fun insertAllUsers() {
        println("inBefore")
        user = authService.registerUser("", "op", "HEll Yeah")
        user2 = authService.registerUser("", "nigger", "HEll Yeah")
        user3 = authService.registerUser("", "lmfao", "HEll Yeah")
        println(user)
        println(user2)
        println(user3)
    }

    @AfterEach
    fun deleteAllUsers() {
        println("inAfter")
        authService.deleteAllUsers()
    }

    @Test
    fun insertIntoDb() {
        val post = postService.insertIntoDb(post.copy(user = user))
        assertThat(postService.checkIfPostExists(post.postId)).isTrue()

    }

    @Test
    fun getAllPostsWhenDbIsNotEmpty() {
        postService.insertIntoDb(post.copy(user = user))
        postService.insertIntoDb(post.copy(user = user2))
        val posts = postService.getAllPosts()
        assertThat(posts).isNotEmpty()
    }

    @Test
    fun getAllPostsWhenDbIsEmpty() {
        val posts = postService.getAllPosts()
        assertThat(posts).isEmpty()
    }

    @Test
    fun getAllFeedPostsWhenUsersHaveNoPosts() {
        val posts = postService.getPostsOfUser(user)
        assertThat(posts).isEmpty()
    }

    @Test
    fun getAllFeedPostsWhenUsersHavePosts() {
        postService.insertIntoDb(post.copy(user = user))
        postService.insertIntoDb(post.copy(user = user2))
        postService.insertIntoDb(post.copy(user = user3))
        val posts = postService.getAllFeedPosts(listOf(user2.userId, user3.userId))
        assertThat(posts).isNotEmpty()
    }

    @Test
    fun deletePostWhenPostExists() {
        val post = postService.insertIntoDb(post.copy(user = user))
        postService.deletePost(post.postId)
        assertThat(postService.checkIfPostExists(post.postId)).isFalse()

    }

    @Test
    fun deleteAllPostsWhenPostDoesNotExist() {
        val exception = assertThrows<PostException> {
            postService.deletePost(1)
        }
        assertThat(exception.message).isEqualTo(POST_DOES_NOT_EXIST_MESSAGE)
    }

    @Test
    fun getPostsOfUserWhenUserHasPosts() {
        postService.insertIntoDb(post.copy(user = user))
        postService.insertIntoDb(post.copy(user = user))
        val posts = postService.getPostsOfUser(user)
        assertThat(posts).isNotEmpty()

    }

    @Test
    fun getPostsOfUserWhenUserHasNoPosts() {
        val posts = postService.getPostsOfUser(user)
        assertThat(posts).isEmpty()
    }

    @Test
    fun checkIfPostExistsWhenItDoes() {
        val post = postService.insertIntoDb(post.copy(user = user))
        val exists = postService.checkIfPostExists(post.postId)
        assertThat(exists).isTrue()
    }

    @Test
    fun checkIfPostExistsWhenItDoesNot() {
        val exists = postService.checkIfPostExists(1)
        assertThat(exists).isFalse()
    }
}