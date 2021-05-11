package com.vaibhav.sociofy.controller

import com.vaibhav.sociofy.controller.utils.getHttpCode
import com.vaibhav.sociofy.controller.utils.getHttpCodeForGet
import com.vaibhav.sociofy.models.response.UserResponse
import com.vaibhav.sociofy.service.MainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class UserController @Autowired constructor(private val mainService: MainService) {

    @PostMapping("/register")
    fun registerUser(
        @RequestParam("username") username: String,
        @RequestParam("email") email: String,
        @RequestParam("password") password: String
    ): ResponseEntity<Any> {
        val response = mainService.registerUser(username = username, email = email, password = password)
        return ResponseEntity(response, response.getHttpCode())
    }

    @PostMapping("/login")
    fun loginUser(
        @RequestParam("email") email: String,
        @RequestParam("password") password: String
    ): ResponseEntity<Any> {
        val response = mainService.loginUser(email = email, password = password)
        return ResponseEntity(response, response.getHttpCode())
    }

    @GetMapping("/userDetailsById")
    fun getUserDetail(
        @RequestParam("userId") userId: Long,
    ): ResponseEntity<Any> {
        val response = mainService.getUserDetailByUserId(userId)
        return ResponseEntity(response, response.getHttpCodeForGet())
    }

    @GetMapping("/userDetailsByEmail")
    fun getUserDetailByEmail(
        @RequestParam("email") email: String,
    ): ResponseEntity<Any> {
        val response = mainService.getUserDetailsByEmail(email)
        return ResponseEntity(response, response.getHttpCodeForGet())
    }

    @GetMapping("/allUsers")
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok(mainService.getAllUsers())
    }

    @GetMapping("/allUsersById")
    fun getAllUserByIds(@RequestParam("userIds") userIds: List<Long>): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok(mainService.getUsersByUserIds(userIds))
    }

    @PutMapping("/updateUserDetail")
    fun updateUserDetails(
        @RequestParam("userId") userId: Long,
        @RequestParam("username", required = false) username: String,
        @RequestParam("bio", required = false) bio: String,
        @RequestParam("profile_img_url", required = false) profileImageUrl: String
    ): ResponseEntity<Any> {
        val response = mainService.updateUserDetails(userId, username, bio, profileImageUrl)
        return ResponseEntity(response, response.getHttpCode())
    }

    @DeleteMapping("/deleteUser")
    fun deleteUser(
        @RequestParam("userId") userId: Long
    ): ResponseEntity<Any> {
        val response = mainService.deleteUser(userId)
        return ResponseEntity(response, response.getHttpCode())
    }

    @DeleteMapping("/deleteAllUsers")
    fun deleteAllUsers(
    ): ResponseEntity<Any> {
        val response = mainService.deleteAllUsers()
        return ResponseEntity(response, response.getHttpCode())
    }
}