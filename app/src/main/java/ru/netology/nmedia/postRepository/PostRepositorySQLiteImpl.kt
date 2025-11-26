package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia.DTO.Post
import ru.netology.nmedia.Entity.PostEntity
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.postRepository.PostRepository

class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {

    override fun get(): LiveData<List<Post> > = dao.getAll().map { posts ->
        posts.map { postEntity ->
            postEntity.toPost()
        }
    }

    override fun save(post: Post) {
       dao.save(PostEntity.fromPost(post))
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun share(id: Long) {
        dao.share(id)
    }

    override fun removeBeId(id: Long) {
        dao.removeById(id)
    }
}