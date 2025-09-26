package ru.netology.nmedia.postRepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.DTO.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private val defaultPost = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        publisher = "21 мая в 18:36",
        content = """Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу.
        Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам:
        от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила,
        которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать
        цепочку перемен → http://netolo.gy/fyb"""",
        countLiked = 0,
        countShare = 0,
        countView = 0,
        likeBeMy = false
    )

    private val data = MutableLiveData(defaultPost)

    override fun get(): LiveData<Post> = data

    override fun like() {
        val post = requireNotNull(data.value) { "post not initialized" }
        if (data.value?.likeBeMy == false) {
            data.value = post.copy(likeBeMy = !post.likeBeMy, countLiked = post.countLiked + 1)
        } else {
            data.value = post.copy(likeBeMy = !post.likeBeMy, countLiked = post.countLiked - 1)
        }
    }

    override fun share() {
        val post = requireNotNull(data.value) { "post not initialized" }
        data.value = post.copy(countShare = post.countShare + 1)
    }
}