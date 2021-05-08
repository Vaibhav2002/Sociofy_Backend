package com.vaibhav.sociofy.models

import java.util.*

data class ErrorResponse(
    val message: String,
    val status: String = "failed"
)
