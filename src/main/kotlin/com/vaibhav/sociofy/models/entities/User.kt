package com.vaibhav.sociofy.models.entities

import java.util.*
import javax.persistence.*

@Entity(name = "user_table")
@Table(name = "user_table")
data class User(
    val username: String = "",
    var password: String = "",
    val email:String ="",
    val profile_img_url: String = "",
    val bio: String = "",
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Long = 0L,

)