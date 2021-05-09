package com.vaibhav.sociofy.service.auth

import com.google.common.truth.Truth.assertThat
import com.vaibhav.sociofy.Exceptions.AuthException
import com.vaibhav.sociofy.models.User
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
class AuthServiceImplTest {

    companion object {
        const val USER_ID = 1L
        const val EMAIL = "vaibhav_test_jaiswal@yahoo.com"
        const val PASSWORD = "vaibhav434424"
        const val USERNAME = "Vaibhav02"
        const val BIO = "Badass coder"
        const val USER_ALREADY_EXISTS_MESSAGE = "User already exists"
        const val USER_DOES_NOT_EXIST_MESSAGE = "User does not exist"
        const val PASSWORD_MISMATCH_MESSAGE = "Password does not match"
        val user = User(
            username = USERNAME, email = EMAIL, password = PASSWORD
        )
    }

    @Autowired
    private lateinit var serviceImpl: AuthServiceImpl

    fun insertUserIntoDb(
        username: String = USERNAME,
        email: String = EMAIL,
        password: String = PASSWORD
    ): User {
        return serviceImpl.registerUser(
            User(
                username,
                email,
                password,
            )
        )
    }


    @Test
    fun insert_user_into_db_when_user_does_not_already_exist() {
        val user = insertUserIntoDb()
        val exists = serviceImpl.checkIfUserExistsById(user.userId)
        assertThat(exists).isTrue()

    }

    @Test
    fun insert_user_into_db_when_user_does_already_exist() {
        insertUserIntoDb()
        val exception = assertThrows<AuthException> {
            insertUserIntoDb()
        }
        assertThat(exception.message).isEqualTo(USER_ALREADY_EXISTS_MESSAGE)
    }


    @Test
    fun login_when_user_does_not_exists() {
        val exception = assertThrows<AuthException> {
            serviceImpl.loginUser(EMAIL, PASSWORD)
        }
        assertThat(exception.message).isEqualTo(USER_DOES_NOT_EXIST_MESSAGE)
    }

    @Test
    fun login_when_user_exists() {
        val user = serviceImpl.registerUser(user)
        println(user.toString())
        assertDoesNotThrow {
            serviceImpl.loginUser(
                email = EMAIL,
                password = PASSWORD
            )
        }
    }

    @Test
    fun login_when_password_is_wrong() {
        serviceImpl.registerUser(user)
        val exception = assertThrows<AuthException>() {
            serviceImpl.loginUser(email = EMAIL, password = "vaibhav32323")
        }
        assertThat(exception.message).isEqualTo(PASSWORD_MISMATCH_MESSAGE)

    }

    @Test
    fun get_user_by_email_when_user_does_not_exist() {
        val exception = assertThrows<AuthException> {
            serviceImpl.getUserByEmail(EMAIL)
        }
        assertThat(exception.message).isEqualTo(USER_DOES_NOT_EXIST_MESSAGE)

    }

    @Test
    fun get_user_by_email_when_user_exists() {
        serviceImpl.registerUser(user)
        val user = serviceImpl.getUserByEmail(EMAIL)
        assertThat(user).isNotNull()
    }

    @Test
    fun get_user_by_id_when_user_does_not_exist() {
        val exception = assertThrows<AuthException> {
            serviceImpl.getUserById(USER_ID)
        }
        assertThat(exception.message).isEqualTo(USER_DOES_NOT_EXIST_MESSAGE)
    }

    @Test
    fun get_user_by_id_when_user_does_exist() {
        val user = insertUserIntoDb()
        assertDoesNotThrow {
            serviceImpl.getUserById(user.userId)
        }
    }

    @Test
    fun delete_user_when_user_does_not_exist() {
        val exception = assertThrows<AuthException> {
            serviceImpl.deleteUser(USER_ID)
        }
        assertThat(exception.message).isEqualTo(USER_DOES_NOT_EXIST_MESSAGE)
    }

    @Test
    fun delete_user_when_user_exists() {
        val user = insertUserIntoDb()
        serviceImpl.deleteUser(user.userId)
        assertThat(serviceImpl.checkIfUserExistsById(user.userId)).isFalse()

    }


    @Test
    fun update_user_when_user_does_not_exist() {
        val exception = assertThrows<AuthException> {
            serviceImpl.updateUserDetails(1, "Vaibhav3233", BIO)
        }
        assertThat(exception.message).isEqualTo(USER_DOES_NOT_EXIST_MESSAGE)
    }

    @Test
    fun update_user_when_user_does_exist() {
        val user = insertUserIntoDb()
        assertDoesNotThrow {
            serviceImpl.updateUserDetails(user.userId, "Vaibahv3233", "Badass Coder")
        }
    }
}