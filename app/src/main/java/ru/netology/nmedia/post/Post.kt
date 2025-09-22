package ru.netology.nmedia.post

data class Post(
    val id: Long,
    val author: String,
    val publisher: String,
    val content: String,
    var countLiked: Long,
    var countShare: Long,
    val countView: Long,
    var likeBeMy: Boolean,
    var shareBeMy: Boolean
)