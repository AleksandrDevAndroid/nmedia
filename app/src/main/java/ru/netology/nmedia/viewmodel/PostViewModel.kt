package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.DTO.Post
import ru.netology.nmedia.postRepository.PostRepository
import ru.netology.nmedia.postRepository.PostRepositoryInMemoryImpl

private val empty = Post(
    id = 0,
    author = "",
    publisher = "",
    content = "",
    countLiked = 0,
    countShare = 0,
    countView = 0,
    likeBeMy = false
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val edited = MutableLiveData(empty)

    fun get(): LiveData<List<Post>> = repository.get()
    fun likeById(id: Long) = repository.likeById(id)
    fun share(id: Long) = repository.share(id)
    fun removeById(id: Long) = repository.removeBeId(id)

    fun save(newContent: String) {
        edited.value?.let { post ->
            if (post.content != newContent) {
                repository.save(post.copy(content = newContent))
            }
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun cancelEdit() {
        edited.value = empty
    }
}