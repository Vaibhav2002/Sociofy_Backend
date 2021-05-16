package com.vaibhav.sociofy.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*



@Entity(name = "like_table")
@Table(name = "like_table")
data class Like(

    @JsonIgnore
    @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY)
    @JoinColumn
    val user:User,

    @ManyToOne(targetEntity = Post::class, fetch = FetchType.LAZY)
    @JoinColumn
    val post:Post,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val likeId:Long = 0
)
