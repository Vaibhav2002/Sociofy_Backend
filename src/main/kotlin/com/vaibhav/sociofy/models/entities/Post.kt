package com.vaibhav.sociofy.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Table(name = "post_table")
@Entity(name = "post_table")
data class Post(
    val gx: Long = 0,
    val description: String = "",
    val imageUrl: String = "",
    val timeStamp: String = System.currentTimeMillis().toString(),
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val postId: Long = 0L,


    @JsonIgnore
    @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY)
    @JoinColumn()
    var user: User = User()
) {

}
