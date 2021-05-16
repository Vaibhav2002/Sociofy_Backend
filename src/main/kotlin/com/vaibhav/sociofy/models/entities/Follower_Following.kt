package com.vaibhav.sociofy.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity(name = "follower_following_table")
@Table(name = "follower_following_table")
data class Follower_Following(

    @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY)
    @JoinColumn
    val follower: User,

    @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY)
    @JoinColumn
    val following: User,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L

)
