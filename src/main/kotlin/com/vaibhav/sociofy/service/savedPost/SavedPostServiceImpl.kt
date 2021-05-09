package com.vaibhav.sociofy.service.savedPost

import com.vaibhav.sociofy.Exceptions.SavedPostException
import com.vaibhav.sociofy.models.SavedPost
import com.vaibhav.sociofy.repository.SavedPostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SavedPostServiceImpl @Autowired constructor(private val savedPostRepository: SavedPostRepository) :
    SavedPostService {

    companion object {
        const val SAVED_POST_DOES_NOT_EXIST_MESSAGE = "Saved Post does not exist"
    }


    override fun savePost(userId: Long, postId: Long) =
        savedPostRepository.save(SavedPost(userId = userId, postId = postId))


    override fun deleteSavedPost(savedPostId: Long) {
        if (!checkIfSavedPostExists(savedPostId))
            throw SavedPostException(SAVED_POST_DOES_NOT_EXIST_MESSAGE)
        savedPostRepository.deleteById(savedPostId)
    }

    override fun deleteAllSavedPostsOfAUSer(userId: Long) {
        savedPostRepository.deleteAllByUserId(userId)
    }

    override fun getAllSavedPostsOfAUser(userId: Long): List<SavedPost> =
        savedPostRepository.getAllByUserId(userId)

    override fun checkIfSavedPostExists(savedPostId: Long) = savedPostRepository.existsById(savedPostId)
}