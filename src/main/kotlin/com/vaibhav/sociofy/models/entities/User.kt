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
    val userId: Long = 0L,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "user")
    val posts: MutableList<Post> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "user")
    val savedPosts: MutableList<SavedPost> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "user")
    val likes:MutableList<Like> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "follower")
    val followers:MutableList<Follower_Following> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "following")
    val following:MutableList<Follower_Following> = mutableListOf()


)