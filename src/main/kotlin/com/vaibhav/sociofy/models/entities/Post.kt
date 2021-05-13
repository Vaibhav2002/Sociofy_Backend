package com.vaibhav.sociofy.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Table(name = "post_table")
@Entity(name = "post_table")
data class Post(
    val description: String = "",
    val imageUrl: String = "",
    val timeStamp: String = System.currentTimeMillis().toString(),
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val postId: Long = 0L,


    @JsonIgnore
    @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY)
    @JoinColumn()
    var user: User = User(),

    @JsonIgnore
    @OneToMany(
        targetEntity = SavedPost::class,
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        mappedBy = "user"
    )
    val savedPosts: MutableList<SavedPost> = mutableListOf()


) {

}
