package com.vaibhav.sociofy.service.auth

import com.vaibhav.sociofy.exceptions.AuthException
import com.vaibhav.sociofy.models.entities.User
import com.vaibhav.sociofy.repository.AuthRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class AuthServiceImpl @Autowired constructor(
    private val encoder: PasswordEncoder,
    private val authRepository: AuthRepository,
) : AuthService {


    override fun registerUser(username: String, email: String, password: String): User {
        if (checkIfUserExistsByEmail(email))
            throw AuthException("User already exists")
        val encodedPassword = encoder.encode(password)
        return insertUserIntoDB(User(username = username, email = email, password = encodedPassword))
    }

    override fun insertUserIntoDB(user: User) = authRepository.save(user)

    override fun checkIfUserExistsByEmail(email: String) = authRepository.findByEmail(email).isPresent

    override fun checkIfUserExistsById(userId: Long) = authRepository.existsById(userId)

    override fun getUserByEmail(email: String) =
        authRepository.findByEmail(email)

    override fun getUserById(userId: Long): User {
        val user = authRepository.findById(userId)
        return if (user.isPresent)
            user.get()
        else
            throw AuthException("User does not exist")
    }

    override fun getUsersByUserIds(userIds: List<Long>) = authRepository.findAllByIds(userIds)

    override fun getAllUsers(): List<User> = authRepository.findAll()

    override fun loginUser(email: String, password: String): User {
        val user = getUserByEmail(email)
        println(user)
        if (user.isPresent) {
            println(user.toString())
            if (encoder.matches(password, user.get().password))
                return user.get();
            else
                throw AuthException("Password does not match")
        } else throw AuthException("User does not exist")


    }

    override fun updateUserDetails(userId: Long, username: String, bio: String, profileImageUrl: String): User {
        val user = getUserById(userId)
        val newUser = user.copy(username = username, bio = bio, profile_img_url = profileImageUrl)
        return insertUserIntoDB(newUser)
    }

    override fun deleteUser(userId: Long): User {
        val user = authRepository.findById(userId)
        if (checkIfUserExistsById(userId)) {
            authRepository.deleteById(userId)
            return user.get()
        } else
            throw AuthException("User does not exist")
    }

    override fun deleteAllUsers() = authRepository.deleteAll()
}