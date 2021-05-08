package com.vaibhav.sociofy.models

import javax.persistence.*

@Entity(name = "like_table")
@Table(name = "like_table")
data class Like(
    private val userId:Long,
    private val postId:Long,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val likeId:Long = 0
)
