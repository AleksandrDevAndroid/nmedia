package ru.netology.nmedia.adapter


import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
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
            like.isChecked = post.likeBeMy
            like.text = Format.format(post.countLiked)
            share.text = Format.format(post.countShare)
            if(post.video != null){
                binding.group.isVisible = true
            }

            attachments.setOnClickListener {
                listener.onPlay(post)
            }
            like.setOnClickListener {
                listener.onLike(post)
            }
            share.setOnClickListener {
                listener.onShare(post)
            }
            menu.setOnClickListener {
                showPopupMenu(it, post)
            }
        }

    }

    @SuppressLint("RestrictedApi")
    private fun showPopupMenu(view: View, post: Post) {
        val context = view.context ?: return

        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.post_menu, popupMenu.menu)

        if (popupMenu.menu is MenuBuilder) {
            val menuBuilder = popupMenu.menu as MenuBuilder
            menuBuilder.setOptionalIconsVisible(true)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true)
        }

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.remove -> {
                    listener.onRemove(post)
                    true
                }

                R.id.edit -> {
                    listener.onEdit(post)
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

}

