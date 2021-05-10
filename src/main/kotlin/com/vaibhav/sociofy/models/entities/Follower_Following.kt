package com.vaibhav.sociofy.models.entities

import javax.persistence.*

@Entity(name = "follower_following_table")
@Table(name = "follower_following_table")
data class Follower_Following(

    private val follower: Long,

    private val following: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id:Long = 0L

)
