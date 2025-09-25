package ru.netology.nmedia.DTO

data class Post(
    val id: Long,
    val author: String,
    val publisher: String,
    val content: String,
    var countLiked: Long,
    var countShare: Long,
    val countView: Long,
    val likeBeMy: Boolean)