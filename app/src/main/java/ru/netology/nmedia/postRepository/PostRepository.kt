package ru.netology.nmedia.postRepository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.DTO.Post

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun share()
}