package com.vaibhav.sociofy.models.response

sealed class Response{
    data class SuccessResponse(val message:String):Response()
    data class ErrorResponse(val message:String):Response()
}
