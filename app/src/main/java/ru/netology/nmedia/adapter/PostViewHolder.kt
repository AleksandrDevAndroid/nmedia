package ru.netology.nmedia.adapter


import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.DTO.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.format.Format

class PostViewHolder(
    private val binding: PostCardBinding,
    private val listener: PostListener,
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
                listener.onLike(post)
            }
            share.setOnClickListener {
                listener.onShare(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_menu)

                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }
                            R.id.edit ->{
                                listener.onEdite(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}
