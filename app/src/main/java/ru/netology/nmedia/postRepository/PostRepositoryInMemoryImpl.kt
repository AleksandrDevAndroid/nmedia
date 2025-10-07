package ru.netology.nmedia.postRepository

import android.icu.util.LocaleData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.DTO.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private val defaultPosts = List(100) { counter ->
        Post(
            id = counter + 1L,
            author = "Нетология. Университет интернет-профессий будущего",
            content = """Post ${counter} Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу.
        Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам:
        от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила,
        которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать
        цепочку перемен → http://netolo.gy/fyb"""",
            countLiked = 0,
            countShare = 0,
            countView = 0,
            likeBeMy = false,
            publisher = ""
        )
    }
    private var nextId = defaultPosts.first().id + 1
    private val data = MutableLiveData(defaultPosts)

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

    }

    override fun removeBeId(id: Long) {
        data.value = data.value?.filter { it.id != id }
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            data.value = listOf(post.copy(id = nextId++)) + data.value.orEmpty()
        } else {
            data.value = data.value?.map {
                if(it.id == post.id){
                    post
                }else {
                    it
                }
            }
        }
    }

    override fun cancelEdit(post: Post) {
        data.value = null
    }
}

