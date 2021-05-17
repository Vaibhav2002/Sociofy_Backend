package com.vaibhav.sociofy.service.auth

import com.vaibhav.sociofy.models.entities.User
import java.util.*

interface AuthService {

    fun registerUser(username: String, email: String, password: String): User

    fun insertUserIntoDB(user: User): User

    fun checkIfUserExistsByEmail(email: String): Boolean

    fun checkIfUserExistsById(userId: Long): Boolean

    fun getUserByEmail(email: String): Optional<User>

    fun getUserById(userId: Long): User

    fun getUsersByUserIds(userIds:List<Long>):List<User>

    fun loginUser(email: String, password: String): User

    fun updateUserDetails(userId: Long, username: String, bio: String, profileImageUrl:String): User

    fun deleteUser(userId: Long):User

    fun deleteAllUsers()

    fun getAllUsers():List<User>

    fun getFollowers(userId: Long):List<User>

    fun getFollowing(userId: Long):List<User>

}