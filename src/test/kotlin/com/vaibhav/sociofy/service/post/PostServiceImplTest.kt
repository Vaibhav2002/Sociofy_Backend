package com.vaibhav.sociofy.service.post

import com.google.common.truth.Truth.assertThat
import com.vaibhav.sociofy.exceptions.PostException
import com.vaibhav.sociofy.models.entities.Post
import com.vaibhav.sociofy.models.entities.User
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
            gx = USER_ID,
            description = POST_DESCRIPTION,
            imageUrl = POST_IMAGE_URL,
            user = User(userId = USER_ID)
        )
    }

    @Autowired
    private lateinit var postService: PostServiceImpl

    @Test
    fun insertIntoDb() {
        val post = postService.insertIntoDb(post)
        assertThat(postService.checkIfPostExists(post.postId)).isTrue()

    }

    @Test
    fun getAllPostsWhenDbIsNotEmpty() {
        postService.insertIntoDb(post)
        postService.insertIntoDb(post)
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
        val posts = postService.getPostsOfUser(User(userId = USER_ID))
        assertThat(posts).isEmpty()
    }

    @Test
    fun getAllFeedPostsWhenUsersHavePosts() {
        postService.insertIntoDb(post)
        postService.insertIntoDb(post.copy(gx = 2))
        postService.insertIntoDb(post.copy(gx = 3))
        val posts = postService.getAllFeedPosts(listOf(User(userId = 2), User(userId = 3)))
        assertThat(posts).isNotEmpty()
    }

    @Test
    fun deletePostWhenPostExists() {
        val post = postService.insertIntoDb(post)
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
        postService.insertIntoDb(post)
        postService.insertIntoDb(post)
        val posts = postService.getPostsOfUser(User(userId = USER_ID))
        assertThat(posts).isNotEmpty()

    }

    @Test
    fun getPostsOfUserWhenUserHasNoPosts() {
        val posts = postService.getPostsOfUser(User(userId = USER_ID))
        assertThat(posts).isEmpty()
    }

    @Test
    fun checkIfPostExistsWhenItDoes() {
        val post = postService.insertIntoDb(post)
        val exists = postService.checkIfPostExists(post.postId)
        assertThat(exists).isTrue()
    }

    @Test
    fun checkIfPostExistsWhenItDoesNot() {
        val exists = postService.checkIfPostExists(1)
        assertThat(exists).isFalse()
    }
}