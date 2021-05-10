package com.vaibhav.sociofy.service.savedPost

import com.google.common.truth.Truth.assertThat
import com.vaibhav.sociofy.exceptions.SavedPostException
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

    @Test
    fun savePost() {
        val saved = service.savePost(USER_ID, POST_ID)
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
        val saved = service.savePost(USER_ID, POST_ID)
        service.deleteSavedPost(saved.saveId)
        val doesItExist = service.checkIfSavedPostExists(saved.saveId)
        assertThat(doesItExist).isFalse()
    }

    @Test
    fun deleteAllSavedPostsOfAUSer() {
        service.savePost(USER_ID, POST_ID)
        service.savePost(USER_ID, 2)
        service.savePost(USER_ID, 3)
        service.deleteAllSavedPostsOfAUSer(USER_ID)
        assertThat(service.getAllSavedPostsOfAUser(USER_ID)).isEmpty()
    }

    @Test
    fun getAllSavedPostsOfAUserWhenThereAreSavedPosts() {
        service.savePost(USER_ID, POST_ID)
        service.savePost(USER_ID,2)
        service.savePost(USER_ID, 3)
        val allposts = service.getAllSavedPostsOfAUser(USER_ID)
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
        val post = service.savePost(USER_ID, POST_ID)
        val exists = service.checkIfSavedPostExists(post.saveId)
        assertThat(exists).isTrue()

    }
}