package ru.netology.nmedia.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.DTO.Post

@Entity()
data class PostEntity(
    @ColumnInfo()
    @PrimaryKey(true)
    val id: Long = 0,
    val author: String,
    val published: String,
    val content: String,
    val likes: Long,
    val share: Long,
    val likedByMe: Boolean,
    val video: String?
) {
    fun toPost(): Post = Post(id, author, published, content, likes, share, likedByMe, video)
    companion object{
        fun fromPost(post: Post): PostEntity = with(post){
            PostEntity(id, author, published, content, likes, share, likedByMe, video)
        }
    }
}