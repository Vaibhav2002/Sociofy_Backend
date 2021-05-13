package com.vaibhav.sociofy.controller

import com.vaibhav.sociofy.controller.utils.getHttpCode
import com.vaibhav.sociofy.controller.utils.getHttpCodeForGet
import com.vaibhav.sociofy.main
import com.vaibhav.sociofy.models.entities.Post
import com.vaibhav.sociofy.models.response.PostResponse
import com.vaibhav.sociofy.service.MainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/post")
class PostController @Autowired constructor(private val mainService: MainService) {

    @PostMapping("/upload")
    fun uploadPost(
        @RequestParam("userId") userId: Long,
        @RequestParam("post_img_url") postImageUrl: String,
        @RequestParam("post_description") postDescription: String,
    ): ResponseEntity<Any> {
        val response = mainService.uploadPost(userId, postDescription, postImageUrl)
        return ResponseEntity(response, response.getHttpCode())
    }

    @GetMapping("/allPosts")
    fun getAllPosts():ResponseEntity<List<PostResponse>>{
        val response = mainService.getAllPosts()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/posts/{userId}")
    fun getAllPostsOfAUser(
        @PathVariable("userId") userId:Long
    ):ResponseEntity<List<PostResponse>>{
        val response = mainService.getPostsOfUser(userId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/postsByIds")
    fun getAllPostsOfAUsers(
        @RequestParam("userIds") userIds:List<Long>
    ):ResponseEntity<List<PostResponse>>{
        val response = mainService.getAllPostByUserIds(userIds)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/postDetail/{postId}")
    fun getPostDetail(
        @PathVariable("postId") postId:Long
    ):ResponseEntity<Any>{
        val response = mainService.getPostDetail(postId)
        return ResponseEntity(response,response.getHttpCodeForGet())
    }

    @DeleteMapping("/deletePost/{postId}")
    fun deletePost(
        @PathVariable("postId") postId:Long
    ):ResponseEntity<Any>{
        val response = mainService.deletePost(postId)
        return ResponseEntity(response,response.getHttpCode())
    }


}