package com.vaibhav.sociofy.service.post

import com.vaibhav.sociofy.exceptions.PostException
import com.vaibhav.sociofy.models.entities.Post
import com.vaibhav.sociofy.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PostServiceImpl @Autowired constructor(private val postRepository: PostRepository) : PostService {

    companion object{
        const val POST_DOES_NOT_EXIST_MESSAGE = "Post does not exist"
    }

    override fun insertIntoDb(post: Post) =
        postRepository.save(post)


    override fun getAllPosts(): List<Post> = postRepository.findAll()

    override fun getAllFeedPosts(userIds: List<Long>): List<Post> = postRepository.getAllFeedPosts(userIds)

    override fun deletePost(postId: Long) =
        if (checkIfPostExists(postId))
            postRepository.deleteById(postId)
        else {

            throw PostException(POST_DOES_NOT_EXIST_MESSAGE)
        }


    override fun deleteAllPosts() = postRepository.deleteAll()

    override fun deleteAllPostsOfAUser(userId: Long) = postRepository.deleteAllByUserId(userId)

    override fun getPostsOfUser(userId: Long): List<Post> = postRepository.getAllPostsOfUSer(userId)

    override fun checkIfPostExists(postId: Long): Boolean = postRepository.existsById(postId)

    override fun getAllPostsByIds(postIds: List<Long>): List<Post> {
        return postRepository.findAllById(postIds)
    }

    override fun getPost(postId: Long): Post {
        val post = postRepository.findById(postId)
        return if(post.isPresent) post.get()
        else throw PostException(POST_DOES_NOT_EXIST_MESSAGE)

    }
}