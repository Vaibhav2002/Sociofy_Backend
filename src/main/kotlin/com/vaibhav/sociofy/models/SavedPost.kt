package com.vaibhav.sociofy.models

import javax.persistence.*

@Entity(name = "saved_post_table")
@Table(name = "saved_post_table")
data class SavedPost(

    private val userId:Long,

    private val postId:Long,

    private val timeStamp:Long = System.currentTimeMillis(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val saveId:Long = 0,


)
