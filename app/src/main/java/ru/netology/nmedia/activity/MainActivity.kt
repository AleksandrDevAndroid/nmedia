package ru.netology.nmedia.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import ru.netology.nmedia.DTO.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<PostViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        adapter(binding)
    }

    private fun MainActivity.adapter(binding: ActivityMainBinding) {
        binding.group.isGone
        val adapter = PostsAdapter(
            object : PostListener {
                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShare(post: Post) {
                    viewModel.share(post.id)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onEdite(post: Post) {
                    viewModel.edit(post)

                }

                override fun cancelEdit(post: Post) {
                    viewModel.cancelEdit()
                }
            }
        )

        binding.ListPosts.adapter = adapter
        setContentView(binding.root)
        applyInsets(binding.root)

        viewModel.get().observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { edited ->
            binding.cancel.setOnClickListener {
                binding.content.setText("")
                binding.content.clearFocus()
                binding.group.isGone = true
                viewModel.cancelEdit()

            }
            if (edited.id != 0L) {
                binding.group.isVisible = true
                AndroidUtils.hideKeyboard(binding.content)
                binding.content.setText(edited.content)
            }


        }

        binding.save.setOnClickListener {
            AndroidUtils.showKeyboard(binding.content)
            val currentText = binding.content.text.trim().toString()
            binding.group.isGone = true
            if (currentText.isBlank()) {
                Toast.makeText(this, getString(R.string.post_content_is_empty), Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            viewModel.save(currentText)
            binding.content.setText("")
            binding.content.clearFocus()
            AndroidUtils.hideKeyboard(binding.content)
        }
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



