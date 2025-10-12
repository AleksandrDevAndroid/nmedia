package ru.netology.nmedia.DTO

import java.time.LocalDate

data class Post(
    val id: Long ,
    val author: String,
    val publisher: String,
    val content: String,
    val countLiked: Long,
    val countShare: Long,
    val countView: Long,
    val likeBeMy: Boolean,
)