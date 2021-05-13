package com.vaibhav.sociofy.service.savedPost

import com.vaibhav.sociofy.exceptions.SavedPostException
import com.vaibhav.sociofy.models.entities.SavedPost
import com.vaibhav.sociofy.repository.SavedPostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class SavedPostServiceImpl @Autowired constructor(private val savedPostRepository: SavedPostRepository) :
    SavedPostService {

    companion object {
        const val SAVED_POST_DOES_NOT_EXIST_MESSAGE = "Saved Post does not exist"
    }


    override fun savePost(savedPost: SavedPost) =
        savedPostRepository.save(savedPost)


    override fun deleteSavedPost(savedPostId: Long) {
        if (!checkIfSavedPostExists(savedPostId))
            throw SavedPostException(SAVED_POST_DOES_NOT_EXIST_MESSAGE)
        savedPostRepository.deleteById(savedPostId)
    }

    override fun deleteAllSavedPostsOfAUSer(userId: Long) {
        savedPostRepository.deleteAllByUser(userId)
    }

    override fun deleteAllByPostId(postId: Long) = savedPostRepository.deleteAllByPost(postId)

    override fun deleteAllSavedPosts() = savedPostRepository.deleteAll()

    override fun getAllSavedPostsOfAUser(userId: Long): List<SavedPost> =
        savedPostRepository.findAllByUser(userId)

    override fun checkIfSavedPostExists(savedPostId: Long) = savedPostRepository.existsById(savedPostId)
}