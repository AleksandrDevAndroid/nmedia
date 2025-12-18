package ru.netology.nmedia.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.DTO.Post
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.postRepository.PostRepository
import ru.netology.nmedia.repository.PostRepositorySQLiteImpl
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
private val empty = Post(
    id = 0,
    author = "",
    published = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
        .toString(),
    content = "",
    likes = 0,
    share = 0,
    likedByMe = false,
    video = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository =
        PostRepositorySQLiteImpl(AppDb.getInstance(application).postDao)

    @RequiresApi(Build.VERSION_CODES.O)
    var edited = MutableLiveData(empty)

    fun get(): LiveData<List<Post>> = repository.get()
    fun likeById(id: Long) = repository.likeById(id)

    fun share(id: Long) = repository.share(id)
    fun removeById(id: Long) = repository.removeBeId(id)

    @RequiresApi(Build.VERSION_CODES.O)
    fun edit(postID: Long?, editText: String, url: String?) {
        repository.get().value?.find { post -> post.id == postID }.let { post ->
            if (post?.video != url) {
                repository.edit(
                    postID, editText, url
                )
            } else if (post?.content != editText) {
                repository.edit(
                    postID,
                    editText,
                    post?.video
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun save(newContent: String, url: String?) {
        edited.value?.let { post ->
            if (post.content != newContent) {
                repository.save(post.copy(content = newContent, video = url))
            }
        }
        edited.value = empty
    }
}

