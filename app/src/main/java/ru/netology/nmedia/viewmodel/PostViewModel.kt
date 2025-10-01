package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.DTO.Post
import ru.netology.nmedia.postRepository.PostRepository
import ru.netology.nmedia.postRepository.PostRepositoryInMemoryImpl

class PostViewModel: ViewModel() {
    private val repository : PostRepository = PostRepositoryInMemoryImpl()

    fun get(): LiveData<List<Post>> = repository.get()
    fun likeById(id: Long) = repository.likeById(id)
    fun share(id: Long) = repository.share(id)
}