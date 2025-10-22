package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.DTO.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.viewmodel.PostViewModel
import java.net.URL
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<PostViewModel>()

    val newPostLauncher =
        registerForActivityResult(NewPostContract) { result ->
            result ?: return@registerForActivityResult
            viewModel.save(result)
        }
    val editPostLauncher =
        registerForActivityResult(NewPostContractEdit) { result ->
            result ?: return@registerForActivityResult
            viewModel.edit(result)
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        adapter(binding)
    }

    private fun MainActivity.adapter(binding: ActivityMainBinding) {

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
                    if(post.video == null){
                        return
                    }
                    val pair = Pair(post.id, post.content)
                    editPostLauncher.launch(pair)
                }

                override fun onPlay(post: Post) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }else
                        startActivity(intent)
                }
            }
        )

        binding.ListPosts.adapter = adapter
        setContentView(binding.root)
        applyInsets(binding.root)

        viewModel.get().observe(this) { posts ->
            adapter.submitList(posts)
        }


        binding.add.setOnClickListener {
            newPostLauncher.launch()
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
}



