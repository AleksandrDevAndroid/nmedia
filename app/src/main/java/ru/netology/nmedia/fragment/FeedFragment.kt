package ru.netology.nmedia.fragment

import  android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.DTO.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.AppActivity.Companion.longArg
import ru.netology.nmedia.activity.AppActivity.Companion.textArg
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFeedBinding.inflate(layoutInflater, container, false)

        val adapter = PostsAdapter(
            object : PostListener {
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
                }

                override fun onEdit(post: Post) {
                    findNavController().navigate(
                        R.id.action_fragmentFeed_to_editFragment,
                        Bundle().apply {
                            longArg = post.id
                            textArg = post.content
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
                    findNavController().navigate(
                        R.id.action_fragmentFeed_to_showPostFragment,
                        Bundle().apply {
                            putLong("postId", post.id)
                        }
                    )
                }
            }
        )

        binding.ListPosts.adapter = adapter
        applyInsets(binding.root)
        viewModel.get().observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }
        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentFeed_to_newPostFragment)
        }
        return binding.root
    }
}

private fun applyInsets(root: ConstraintLayout) {
    ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(
            systemBars.left,
            systemBars.top,
            systemBars.right,
            systemBars.bottom
        )
        insets
    }
}




