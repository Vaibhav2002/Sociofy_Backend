package com.vaibhav.sociofy.controller

import com.vaibhav.sociofy.controller.utils.getHttpCode
import com.vaibhav.sociofy.models.response.PostResponse
import com.vaibhav.sociofy.models.response.Response
import com.vaibhav.sociofy.service.MainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/likes")
class LikeController @Autowired constructor(private val mainService: MainService) {


    @PostMapping("/likePost")
    fun likePost(
        @RequestParam("userId") userId: Long,
        @RequestParam("postId") postId: Long,
    ): ResponseEntity<Response> {
        val response = mainService.likePost(userId, postId)
        return ResponseEntity(response, response.getHttpCode())
    }

    @DeleteMapping("/dislikePost")
    fun dislikePost(
        @RequestParam("userId") userId: Long,
        @RequestParam("postId") postId: Long,
    ): ResponseEntity<Response> {
        val response = mainService.disLikePost(userId, postId)
        return ResponseEntity(response, response.getHttpCode())
    }

    @GetMapping("/allLikedPostsOfUser")
    fun getAllLikedPostsOfUser(
        @RequestParam("userId") userId: Long,
    ): ResponseEntity<List<PostResponse>> {
        val response = mainService.getAllLikedPostsOfAUser(userId)
        return ResponseEntity.ok(response)
    }


    @GetMapping("/allLikersOfAPost")
    fun getAllLikersOfAPost(
        @RequestParam("postId") postId: Long,
    ): ResponseEntity<List<Long>> {
        val response = mainService.getAllLikersOfAPost(postId)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/allLikesOfUser")
    fun deleteAllLikesOfAUser(
        @RequestParam("userId") userId: Long,
    ): ResponseEntity<Response> {
        val response = mainService.deleteAllLikesOfAUser(userId)
        return ResponseEntity(response, response.getHttpCode())
    }

    @DeleteMapping("/allLikesOfPost")
    fun deleteAllLikesOfAPost(
        @RequestParam("postId") postId: Long,
    ): ResponseEntity<Response> {
        val response = mainService.deleteAllLikesOfAPost(postId)
        return ResponseEntity(response, response.getHttpCode())
    }

    @DeleteMapping("/deleteAll")
    fun deleteAllLikes(): ResponseEntity<Response> {
        val response = mainService.deleteAllLikes()
        return ResponseEntity(response, response.getHttpCode())
    }


}