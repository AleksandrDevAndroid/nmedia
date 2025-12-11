package ru.netology.nmedia.postRepository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.DTO.Post

interface PostRepository {
    fun get(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun share(id: Long)
    fun removeBeId(id: Long)
    fun save(post: Post)
    fun edit(id: Long?, content: String, url: String?)
}