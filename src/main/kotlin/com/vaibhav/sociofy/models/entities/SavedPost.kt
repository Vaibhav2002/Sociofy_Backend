package com.vaibhav.sociofy.models.entities

import javax.persistence.*

@Entity(name = "saved_post_table")
@Table(name = "saved_post_table")
data class SavedPost(

     val userId:Long,

     val postId:Long,

     val timeStamp:Long = System.currentTimeMillis(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val saveId:Long = 0,


)
