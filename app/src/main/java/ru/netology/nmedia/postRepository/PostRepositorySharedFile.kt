package ru.netology.nmedia.postRepository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.DTO.Post
import java.lang.reflect.Type

class PostRepositorySharedFile(private val context: Context) : PostRepository {

    private var posts = readPosts()
        set(value) {
            field = value
            sync()
        }
    private var nextId = (posts.maxByOrNull { it.id }?.id ?: 0L) + 1
    private val data = MutableLiveData(posts)


    override fun get(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        val posts = data.value.orEmpty()
        data.value = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    likeBeMy = !post.likeBeMy,
                    countLiked = if (!post.likeBeMy) {
                        post.countLiked + 1
                    } else post.countLiked - 1
                )
            } else {
                post
            }
        }
        sync()
    }

    override fun share(id: Long) {
        val post = data.value.orEmpty()
        data.value = post.map { post ->
            if (post.id == id) {
                post.copy(
                    countShare = post.countShare + 1
                )
            } else post
        }
        sync()
    }

    override fun removeBeId(id: Long) {
        data.value = data.value?.filter { it.id != id }
        sync()
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            data.value = listOf(post.copy(id = nextId++)) + data.value.orEmpty()
        } else {
            data.value = data.value?.map {
                if (it.id == post.id) {
                    post
                } else {
                    it
                }
            }
        }
        sync()
    }


    private fun readPosts(): List<Post> {
        val file = context.filesDir.resolve(POST_FILE)
        return if (file.exists()) {
            context.openFileInput(POST_FILE).bufferedReader().use {
                gson.fromJson(it, postType)
            }
        } else emptyList()
    }

    private fun sync() {
        context.openFileOutput(POST_FILE, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(data.value))
        }
    }


    companion object {
        const val POST_FILE = "posts.json"
        val gson = Gson()
        val postType: Type = TypeToken.getParameterized(List::class.java, Post::class.java).type

    }
}


