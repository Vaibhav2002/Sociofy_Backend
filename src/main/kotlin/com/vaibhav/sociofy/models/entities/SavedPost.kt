package com.vaibhav.sociofy.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity(name = "saved_post_table")
@Table(name = "saved_post_table")
data class SavedPost(


    @JsonIgnore
    @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY)
    @JoinColumn
    val user: User = User(),

    @ManyToOne(targetEntity = Post::class,fetch = FetchType.LAZY)
    @JoinColumn
    val post:Post = Post(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val saveId: Long = 0,



    val timeStamp: Long = System.currentTimeMillis(),

)
