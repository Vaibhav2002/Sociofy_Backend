package com.vaibhav.sociofy.controller

import com.vaibhav.sociofy.Exceptions.AuthException
import com.vaibhav.sociofy.models.ErrorResponse
import com.vaibhav.sociofy.models.User
import com.vaibhav.sociofy.service.auth.AuthServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class UserController @Autowired constructor(private val authServiceImpl: AuthServiceImpl) {

    @PostMapping("/register")
    fun registerUser(
        @RequestParam("username") username: String,
        @RequestParam("email") email: String,
        @RequestParam("password") password: String
    ): ResponseEntity<Any> {
        return try {
            val user = authServiceImpl.registerUser(User(username = username, email = email, password = password))
            ResponseEntity.ok(user)
        } catch (e: AuthException) {
            ResponseEntity(ErrorResponse(e.message), HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/login")
    fun loginUser(
        @RequestParam("email") email: String,
        @RequestParam("password") password: String
    ): ResponseEntity<Any> {
        return try {
            val user = authServiceImpl.loginUser(email, password)
            ResponseEntity.ok(user)
        } catch (e: AuthException) {
            ResponseEntity(ErrorResponse(e.message), HttpStatus.BAD_REQUEST)
        }
    }
}