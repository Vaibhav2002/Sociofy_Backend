package com.vaibhav.sociofy.service.post

import com.vaibhav.sociofy.Exceptions.PostException
import com.vaibhav.sociofy.models.Post
import com.vaibhav.sociofy.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PostServiceImpl @Autowired constructor(private val postRepository: PostRepository) : PostService {

    override fun insertIntoDb(post: Post) =
        postRepository.save(post)


    override fun getAllPosts(): List<Post> = postRepository.findAll()

    override fun getAllFeedPosts(userIds: List<Long>): List<Post> = postRepository.getAllFeedPosts(userIds)

    override fun deletePost(postId: Long) =
        if (checkIfPostExists(postId))
            postRepository.deleteById(postId)
        else
            throw PostException("Post does not exist")


    override fun deleteAllPosts() = postRepository.deleteAll()

    override fun getPostsOfUser(userId: Long): List<Post> = postRepository.getAllPostsOfUSer(userId)

    override fun checkIfPostExists(postId: Long): Boolean = postRepository.existsById(postId)

    override fun getAllPostsByIds(postIds: List<Long>): List<Post> {
        return postRepository.findAllById(postIds)
    }
}