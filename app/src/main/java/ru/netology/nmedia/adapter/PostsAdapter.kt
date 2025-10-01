package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.DTO.Post
import ru.netology.nmedia.databinding.PostCardBinding

typealias likeClickListener = (post: Post) -> Unit
typealias shareClickListener = (post: Post) -> Unit

class PostsAdapter(
    private val likeClickListener: likeClickListener,
    private var shareClickListener: shareClickListener
) :
    RecyclerView.Adapter<PostViewHolder>() {

    var data: List<Post> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PostViewHolder(binding, likeClickListener,shareClickListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}