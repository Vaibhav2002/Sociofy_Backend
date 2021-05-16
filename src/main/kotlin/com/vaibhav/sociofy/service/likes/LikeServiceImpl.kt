package com.vaibhav.sociofy.service.likes

import com.vaibhav.sociofy.exceptions.LikeException
import com.vaibhav.sociofy.models.entities.Like
import com.vaibhav.sociofy.models.entities.Post
import com.vaibhav.sociofy.models.entities.User
import com.vaibhav.sociofy.repository.LikeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class LikeServiceImpl @Autowired constructor(private val likeRepository: LikeRepository) : LikeService {

    override fun likePost(user: User, post: Post): Like {
        if (exists(user.userId, post.postId))
            throw LikeException("Like already exists")
        return likeRepository.save(Like(user, post))
    }


    override fun dislikePost(userId: Long, postId: Long) {
        if (!exists(userId, postId))
            throw LikeException("Like does not exist")
        likeRepository.disLike(userId, postId)
    }

    override fun getLikeCount(postId: Long) = likeRepository.getLikeCount(postId)

    override fun exists(userId: Long, postId: Long) = likeRepository.getLike(userId, postId).isPresent

    //will be refactored
    override fun isLikedByUser(userId: Long) = true

    override fun deleteAllOfAUser(userId: Long) = likeRepository.deleteAllByUserId(userId)
    override fun deleteAll() = likeRepository.deleteAll()
    override fun deleteAllByPostId(postId: Long) = likeRepository.deleteAllByPostId(postId)

    override fun getAllLikedPosts(userId: Long): List<Post> = likeRepository.findAllLikedPostIdsByUserId(userId)

    override fun getAllLikersOfAPost(postId: Long): List<Long> = likeRepository.findAllLikersOfAPost(postId)

    override fun getLike(userId: Long, postId: Long) = likeRepository.getLike(userId, postId).get()
}