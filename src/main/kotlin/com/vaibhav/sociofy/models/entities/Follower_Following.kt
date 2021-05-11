package com.vaibhav.sociofy.models.entities

import javax.persistence.*

@Entity(name = "follower_following_table")
@Table(name = "follower_following_table")
data class Follower_Following(

    private val followerId: Long,

    private val followingId: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id:Long = 0L

)
