package com.vaibhav.sociofy.service.auth

import com.vaibhav.sociofy.Exceptions.AuthException
import com.vaibhav.sociofy.models.User
import com.vaibhav.sociofy.repository.AuthRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl @Autowired constructor(
    private val encoder: PasswordEncoder,
    private val authRepository: AuthRepository,
) : AuthService {


    override fun registerUser(user: User): User {
        if (checkIfUserExistsByEmail(user.email))
            throw AuthException("User already exists")
        user.password = encoder.encode(user.password)
        return insertUserIntoDB(user)
    }

    override fun insertUserIntoDB(user: User) = authRepository.save(user)

    override fun checkIfUserExistsByEmail(email: String) = authRepository.getUserByEmail(email) != null

    override fun checkIfUserExistsById(userId: Long) = authRepository.existsById(userId)

    override fun getUserByEmail(email: String) =
        authRepository.getUserByEmail(email) ?: throw AuthException("User does not exist")

    override fun getUserById(userId: Long): User =
        authRepository.findById(userId).orElseThrow {
            AuthException("User does not exist")
        }

    override fun loginUser(email: String, password: String): User {
        val user = getUserByEmail(email)
        println(user.toString())
        if (encoder.matches(password, user.password))
            return user;
        else
            throw AuthException("Password does not match")

    }

    override fun updateUserDetails(userId: Long, username: String, bio: String): User {
        val user = getUserById(userId)
        val newUser = user.copy(username = username, bio = bio)
        return insertUserIntoDB(newUser)
    }

    override fun deleteUser(userId: Long) {
        if (checkIfUserExistsById(userId)) {
            authRepository.deleteById(userId)
        } else
            throw AuthException("User does not exist")
    }

}