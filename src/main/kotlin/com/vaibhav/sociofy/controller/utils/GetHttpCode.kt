package com.vaibhav.sociofy.controller.utils

import com.vaibhav.sociofy.models.response.Response
import org.springframework.http.HttpStatus

fun Any.getHttpCode(): HttpStatus {
    return if (this is Response.ErrorResponse)
        HttpStatus.BAD_REQUEST
    else
        HttpStatus.OK
}
fun Any.getHttpCodeForGet(): HttpStatus {
    return if (this is Response.ErrorResponse)
        HttpStatus.NO_CONTENT
    else
        HttpStatus.OK
}
