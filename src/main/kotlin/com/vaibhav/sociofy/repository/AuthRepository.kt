package com.vaibhav.sociofy.repository

import com.vaibhav.sociofy.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AuthRepository : JpaRepository<User, Long> {

    @Query("SELECT u FROM user_table u WHERE u.email = ?1")
    fun getUserByEmail(email: String): User?
}