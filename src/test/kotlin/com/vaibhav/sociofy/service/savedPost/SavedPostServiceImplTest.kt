package com.vaibhav.sociofy.service.savedPost

import com.google.common.truth.Truth.assertThat
import com.vaibhav.sociofy.exceptions.SavedPostException
import com.vaibhav.sociofy.models.entities.Post
import com.vaibhav.sociofy.models.entities.SavedPost
import com.vaibhav.sociofy.service.auth.AuthServiceImpl
import com.vaibhav.sociofy.service.post.PostServiceImpl
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
class SavedPostServiceImplTest {

    companion object {
        const val USER_ID = 1L
        const val POST_ID = 1L
        const val SAVED_POST_ID = 1L
        const val SAVED_POST_DOES_NOT_EXIST_MESSAGE = "Saved Post does not exist"
    }

    @Autowired
    private lateinit var service: SavedPostServiceImpl

    @Autowired
    private lateinit var authService: AuthServiceImpl

    @Autowired
    private lateinit var postService: PostServiceImpl

    @Test
    fun savePost() {
        val user = authService.registerUser("", "", "Hello")
        val post = postService.insertIntoDb(Post(user = user))
        val saved = service.savePost(SavedPost(user, post))
        val allSaved = service.checkIfSavedPostExists(saved.saveId)
        println(allSaved)
        assertThat(allSaved).isTrue()
    }

    @Test
    fun deleteSavedPostWhenSavedPostDoesNotExist() {
        val exception = assertThrows<SavedPostException> {
            service.deleteSavedPost(SAVED_POST_ID)
        }
        assertThat(exception.message).isEqualTo(SAVED_POST_DOES_NOT_EXIST_MESSAGE)

    }

    @Test
    fun deleteSavedPostWhenSavedPostDoesExist() {
        val user = authService.registerUser("", "", "Hello")
        val post = postService.insertIntoDb(Post(user = user))
        val saved = service.savePost(SavedPost(user, post))
        service.deleteSavedPost(saved.saveId)
        val doesItExist = service.checkIfSavedPostExists(saved.saveId)
        assertThat(doesItExist).isFalse()
    }

    @Test
    fun deleteAllSavedPostsOfAUSer() {
        val user = authService.registerUser("", "", "Hello")
        val post = postService.insertIntoDb(Post(user = user))
        val post2 = postService.insertIntoDb(Post(user = user))
        val post3 = postService.insertIntoDb(Post(user = user))
        service.savePost(SavedPost(user, post))
        service.savePost(SavedPost(user, post2))
        service.savePost(SavedPost(user, post3))
        service.deleteAllSavedPostsOfAUSer(USER_ID)
        assertThat(service.getAllSavedPostsOfAUser(USER_ID)).isEmpty()
    }

    @Test
    fun getAllSavedPostsOfAUserWhenThereAreSavedPosts() {
        val user = authService.registerUser("", "", "Hello")
        val post = postService.insertIntoDb(Post(user = user))
        val post2 = postService.insertIntoDb(Post(user = user))
        val post3 = postService.insertIntoDb(Post(user = user))
        val user2 = authService.registerUser(",", ",", "OhMahGo")
        service.savePost(SavedPost(user2, post))
        service.savePost(SavedPost(user2, post2))
        service.savePost(SavedPost(user2, post3))
        service.savePost(SavedPost(user, post))
        val allposts = service.getAllSavedPostsOfAUser(user2.userId)
        println(allposts.size)
        assertThat(allposts).isNotEmpty()
    }

    @Test
    fun getAllSavedPostsOfAUserWhenThereAreNoSavedPosts() {
        val allposts = service.getAllSavedPostsOfAUser(USER_ID)
        assertThat(allposts).isEmpty()
    }


    @Test
    fun checkIfSavedPostExistsWhenItDoesNot() {
        val exists = service.checkIfSavedPostExists(USER_ID)
        assertThat(exists).isFalse()
    }

    @Test
    fun checkIfSavedPostExistsWhenItDoes() {
        val user = authService.registerUser("", "", "Hello")
        val post = postService.insertIntoDb(Post(user = user))
        val saved = service.savePost(SavedPost(user,post))
        val exists = service.checkIfSavedPostExists(saved.saveId)
        assertThat(exists).isTrue()

    }
}