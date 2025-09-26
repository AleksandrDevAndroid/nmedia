package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.format.Format
import ru.netology.nmedia.DTO.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.postRepository.PostRepository
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<PostViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyInsets(binding.root)

        viewModel.get().observe(this) { post ->
            with(binding) {
                author.text = post.author
                publisher.text = post.publisher
                content.text = post.content
                countViewShare.text = Format.format(post.countShare)
                countViewLikes.text = Format.format(post.countLiked)
                countViewWatch.text = Format.format(post.countView)

                like.setImageResource(
                    if (post.likeBeMy) {
                        R.drawable.baseline_favorite_24
                    } else R.drawable.sharp_favorite_24)

                binding.countViewLikes.text = Format.format(post.countLiked)
                binding.countViewShare.text = Format.format(post.countShare)
            }
        }
        binding.like.setOnClickListener {
            viewModel.like()
        }
        binding.share.setOnClickListener {
            viewModel.share()
        }
    }
}

private fun applyInsets(root: ConstraintLayout) {
    ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    }

}



