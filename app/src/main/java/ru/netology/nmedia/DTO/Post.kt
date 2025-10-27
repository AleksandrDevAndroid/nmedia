package ru.netology.nmedia.DTO
data class Post(
    val id: Long = 0,
    val author: String,
    val publisher: String,
    val content: String,
    val countLiked: Long,
    val countShare: Long,
    val countView: Int,
    val likeBeMy: Boolean,
    val video : String?
)