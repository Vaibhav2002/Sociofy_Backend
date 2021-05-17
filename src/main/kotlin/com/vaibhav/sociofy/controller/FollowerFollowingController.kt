package com.vaibhav.sociofy.controller

import com.vaibhav.sociofy.controller.utils.getHttpCode
import com.vaibhav.sociofy.models.response.Response
import com.vaibhav.sociofy.service.MainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/follow")
class FollowerFollowingController @Autowired constructor(private val mainService: MainService) {


    @PostMapping("/followUser")
    fun followUser(
        @RequestParam("followerId") followerId: Long,
        @RequestParam("followingId") followingId: Long,
    ): ResponseEntity<Response> {
        val response = mainService.followUser(followerId,followingId)
        return ResponseEntity(response, response.getHttpCode())
    }

    @PostMapping("/unfollowUser")
    fun unfollowUser(
        @RequestParam("followerId") followerId: Long,
        @RequestParam("followingId") followingId: Long,
    ): ResponseEntity<Response> {
        val response = mainService.unfollowUser(followerId,followingId)
        return ResponseEntity(response, response.getHttpCode())
    }

    @GetMapping("/getAllFollowingIds")
    fun getAllFollowing(
        @RequestParam("userId") userId:Long
    ):ResponseEntity<List<Long>>{
        return ResponseEntity.ok(mainService.getAllFollowingIdsOfAUser(userId))
    }

    @GetMapping("/getAllFollowersIds")
    fun getAllFollowers(
        @RequestParam("userId") userId:Long
    ):ResponseEntity<List<Long>>{
        return ResponseEntity.ok(mainService.getAllFollowerIdsOfUser(userId))
    }

}