package com.vaibhav.sociofy.repository

import com.vaibhav.sociofy.models.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AuthRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String): Optional<User>

    @Query("SELECT u FROM user_table u WHERE u.userId IN ?1")
    fun findAllByIds(userIds:List<Long>):List<User>
}