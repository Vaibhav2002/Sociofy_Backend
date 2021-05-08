package com.vaibhav.sociofy.service.auth

import com.vaibhav.sociofy.models.User
import org.springframework.stereotype.Service

interface AuthService {

    fun registerUser(user: User): User

    fun insertUserIntoDB(user: User): User

    fun checkIfUserExistsByEmail(email: String): Boolean

    fun checkIfUserExistsById(userId: Long): Boolean

    fun getUserByEmail(email: String):User

    fun getUserById(userId: Long): User

    fun loginUser(email: String, password: String): User

    fun updateUserDetails(userId: Long, username: String, bio: String): User

    fun deleteUser(userId: Long)

}