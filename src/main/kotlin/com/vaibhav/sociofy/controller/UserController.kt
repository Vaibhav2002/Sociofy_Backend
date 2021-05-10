package com.vaibhav.sociofy.controller

import com.vaibhav.sociofy.models.response.Response
import com.vaibhav.sociofy.models.response.UserDetailsResponse
import com.vaibhav.sociofy.service.MainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
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
        val user = mainService.registerUser(username = username, email = email, password = password)
        return if (user is Response.ErrorResponse)
            ResponseEntity(user, HttpStatus.BAD_REQUEST)
        else
            ResponseEntity.ok(user as UserDetailsResponse)
    }

    @GetMapping("/login")
    fun loginUser(
        @RequestParam("email") email: String,
        @RequestParam("password") password: String
    ): ResponseEntity<Any> {
        val response = mainService.loginUser(email = email, password = password)
        return if (response is Response.ErrorResponse)
            ResponseEntity(response, HttpStatus.BAD_REQUEST)
        else
            ResponseEntity.ok(response as UserDetailsResponse)
    }
}