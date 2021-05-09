package com.vaibhav.sociofy.models.response

import java.util.*

data class ErrorResponse(
    val message: String,
    val status: String = "failed"
)
