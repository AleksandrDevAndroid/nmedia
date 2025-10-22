package ru.netology.nmedia.activity

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.DTO.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityEditPostBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        applyInsets(binding)
        val postId = intent.getLongExtra("POST_ID", 0L)
        intent.getStringExtra("POST_CONTENT").let {
            binding.edit.setText(it)
        }

        binding.ok.setOnClickListener {
            if (binding.edit.text.isNullOrBlank()) {
                Toast.makeText(this, getString(R.string.post_content_is_empty), Toast.LENGTH_SHORT)
                    .show()
                setResult(RESULT_CANCELED)
            } else {
                val intent = Intent().apply {
                    putExtra("POST_ID", postId)
                    putExtra("POST_CONTENT", binding.edit.text.toString())
                }
                setResult(RESULT_OK, intent)
            }
            finish()
        }

        binding.cancel.setOnClickListener {
            val intent = Intent()
            setResult(RESULT_CANCELED, intent)
            finish()
        }
    }

}


private fun EditPostActivity.applyInsets(binding: ActivityEditPostBinding) {
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    }
}

object NewPostContractEdit : ActivityResultContract<Pair<Long, String>, Pair<Long, String>?>() {
    override fun createIntent(
        context: Context,
        input: Pair<Long, String>
    ): Intent = Intent(context, EditPostActivity::class.java).apply {
        putExtra("POST_ID", input.first)
        putExtra("POST_CONTENT", input.second)
    }

    override fun parseResult(
        resultCode: Int,
        intent: Intent?
    ): Pair<Long, String>? {
        return if (resultCode == RESULT_OK) {
            val id = intent?.getLongExtra("POST_ID", 0L) ?: 0L
            val content = intent?.getStringExtra("POST_CONTENT") ?: ""
          Pair(id,content)
        } else {
            null
        }
    }
}