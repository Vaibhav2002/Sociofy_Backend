package com.vaibhav.sociofy.repository

import com.vaibhav.sociofy.models.SavedPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface SavedPostRepository : JpaRepository<SavedPost, Long> {

    @Transactional
    fun deleteAllByUserId(userId: Long)


    fun getAllByUserId(userId: Long): List<SavedPost>
}