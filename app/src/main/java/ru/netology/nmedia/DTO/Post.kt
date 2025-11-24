package ru.netology.nmedia.DTO

data class Post(
    val id: Long = 0,
    val author: String,
    val published: String,
    val content: String,
    val likes: Long,
    val share: Long,
    val likedByMe: Boolean,
    val video: String?
)