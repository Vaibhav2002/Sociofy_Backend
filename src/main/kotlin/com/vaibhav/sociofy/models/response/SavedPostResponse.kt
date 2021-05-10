package com.vaibhav.sociofy.models.response

data class SavedPostResponse(
    val savedPostData:PostResponse,
    val saveId:Long,
    val saveTimeStamp:Long
)