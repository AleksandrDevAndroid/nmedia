package ru.netology.nmedia.fragment


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.DTO.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.AppActivity.Companion.longArg
import ru.netology.nmedia.activity.AppActivity.Companion.textArg
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.FragmentShowPostBinding
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import kotlin.getValue


class ShowPostFragment() : Fragment() {
    val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val postId = arguments?.getLong("postId")
        val binding = PostCardBinding.inflate(inflater, container, false)
        val viewHolder = PostViewHolder(binding, object : PostListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.share(post.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_post_share))
                startActivity(shareIntent)

            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
                findNavController().navigateUp()
            }

            override fun onEdit(post: Post) {
                findNavController().navigate(
                    R.id.action_showPostFragment_to_editFragment,
                    Bundle().apply {
                        longArg = post.id
                        textArg = post.content
                        putString("url",post.video)
                    }
                )

            }

            override fun onPlay(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                activity?.let {
                    if (intent.resolveActivity(it.packageManager) != null) {
                        startActivity(intent)
                    } else
                        startActivity(intent)
                }

            }

            override fun onOpen(post: Post) {
            }
        })

        viewModel.get().observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == postId } ?: run {
                findNavController().navigateUp()
                return@observe
            }
            viewHolder.bind(post)
        }
        return binding.root
    }
}