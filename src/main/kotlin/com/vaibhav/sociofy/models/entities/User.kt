package com.vaibhav.sociofy.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity(name = "user_table")
@Table(name = "user_table")
data class User(
    val username: String = "",
    @JsonIgnore
    var password: String = "",
    @JsonIgnore
    var email: String = "",
    val profile_img_url: String = "",
    val bio: String = "",
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Long = 0L,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "user")
    var posts:MutableList<Post>  = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "user")
    var savedPosts:MutableList<SavedPost> = mutableListOf()
)