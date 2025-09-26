package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.DTO.Post
import ru.netology.nmedia.postRepository.PostRepository
import ru.netology.nmedia.postRepository.PostRepositoryInMemoryImpl

class PostViewModel: ViewModel() {
    private val repository : PostRepository = PostRepositoryInMemoryImpl()

    fun get(): LiveData<Post> = repository.get()
    fun like() = repository.like()
    fun share() = repository.share()
}