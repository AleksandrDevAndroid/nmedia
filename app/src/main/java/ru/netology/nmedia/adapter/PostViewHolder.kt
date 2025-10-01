package ru.netology.nmedia.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.DTO.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.format.Format

class PostViewHolder(
    private val binding: PostCardBinding,
    private val likeClickListener: likeClickListener,
    private val shareClickListener: shareClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            publisher.text = post.publisher
            content.text = post.content
            countViewShare.text = Format.format(post.countShare)
            countViewLikes.text = Format.format(post.countLiked)
            countViewWatch.text = Format.format(post.countView)

            like.setImageResource(
                if (post.likeBeMy) R.drawable.baseline_favorite_24 else R.drawable.sharp_favorite_24
            )

            countViewLikes.text = Format.format(post.countLiked)
            countViewShare.text = Format.format(post.countShare)

            like.setOnClickListener {
                likeClickListener(post)
            }
            share.setOnClickListener {
                shareClickListener(post)
            }
        }
    }
}
