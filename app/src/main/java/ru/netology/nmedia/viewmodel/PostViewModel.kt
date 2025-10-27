package ru.netology.nmedia.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.DTO.Post
import ru.netology.nmedia.postRepository.PostRepository
import ru.netology.nmedia.postRepository.PostRepositorySharedFile

private val empty = Post(
    id = 0,
    author = "",
    publisher = "",
    content = "",
    countLiked = 0,
    countShare = 0,
    countView = 0,
    likeBeMy = false,
    video = null
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySharedFile(application)
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

    fun edit(pair: Pair<Long, String>) {
        repository.get().value?.find { it.id == pair.first }?.let { post ->
            repository.save(post.copy(content = pair.second))
        }
    }

}

