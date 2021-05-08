package com.vaibhav.sociofy.service

import com.vaibhav.sociofy.service.auth.AuthServiceImpl
import com.vaibhav.sociofy.service.post.PostServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MainService @Autowired constructor(
    private val authServiceImpl: AuthServiceImpl,
    private val postServiceImpl: PostServiceImpl
) {




}