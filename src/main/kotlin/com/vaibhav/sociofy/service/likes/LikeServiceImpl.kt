package com.vaibhav.sociofy.service.likes

import com.vaibhav.sociofy.models.entities.Like
import com.vaibhav.sociofy.repository.LikeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LikeServiceImpl @Autowired constructor(private val likeRepository: LikeRepository) : LikeService {

    override fun likePost(userId: Long, postId: Long) {
        likeRepository.save(Like(userId, postId))
    }

    override fun dislikePost(userId: Long, postId: Long) {
        likeRepository.delete(Like(userId, postId))
    }

    override fun getLikeCount(postId: Long) = likeRepository.getLikeCount(postId)

    override fun isLikedByUser(userId: Long) = likeRepository.existsByUserId(userId)

    override fun deleteAllOfAUser(userId: Long) = likeRepository.deleteAllByUserId(userId)
    override fun deleteAll() = likeRepository.deleteAll()
    override fun deleteAllByPostId(postId: Long) = likeRepository.deleteAllByPostId(postId)

    override fun getAllLikedPostsIds(userId: Long): List<Long> = likeRepository.findAllPostIdsByUserId(userId)

    override fun getAllLikersOfAPost(postId: Long): List<Long> = likeRepository.findAllLikersOfAPost(postId)
}