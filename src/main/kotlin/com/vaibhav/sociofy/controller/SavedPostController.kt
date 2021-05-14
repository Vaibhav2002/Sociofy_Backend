package com.vaibhav.sociofy.controller

import com.vaibhav.sociofy.controller.utils.getHttpCode
import com.vaibhav.sociofy.models.response.Response
import com.vaibhav.sociofy.models.response.SavedPostResponse
import com.vaibhav.sociofy.service.MainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/saved")
class SavedPostController @Autowired constructor(private val mainService: MainService) {


    @PostMapping("/savePost")
    fun savePost(
        @RequestParam("userId") userId: Long,
        @RequestParam("postId") postId: Long,
    ): ResponseEntity<Any> {
        val response = mainService.savePost(userId, postId)
        return ResponseEntity(response, response.getHttpCode())
    }

    @GetMapping("/allOfUser")
    fun getAllSavedPostsOfUser(
        @RequestParam("userId") userId: Long
    ): ResponseEntity<List<SavedPostResponse>> {
        return ResponseEntity.ok(mainService.getSavedPostsOfUser(userId))
    }

    @DeleteMapping("/delete")
    fun deleteSavedPost(
        @RequestParam("savedId") savedPostId: Long
    ): ResponseEntity<Response> {
        val response = mainService.deleteSavedPost(savedPostId)
        return ResponseEntity(response, response.getHttpCode())
    }

    @DeleteMapping("/deleteAllOfUser")
    fun deleteAllSavedPostOfAUser(
        @RequestParam("userId") userId: Long
    ): ResponseEntity<Response> {
        val response = mainService.deleteAllSavedPostsOfAUser(userId)
        return ResponseEntity(response, response.getHttpCode())
    }
}






