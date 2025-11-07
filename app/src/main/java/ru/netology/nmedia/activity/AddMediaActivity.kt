package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentAddVideoBinding

class AddVideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = FragmentAddVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        applyInsets()
        intent.getStringExtra("POST_CONTENT").let {
            binding.edit.setText(it)
        }

        binding.ok.setOnClickListener {
            if (binding.edit.text.isNullOrBlank()) {
                Toast.makeText(this, getString(R.string.post_content_is_empty), Toast.LENGTH_SHORT)
                    .show()
                setResult(RESULT_CANCELED)
            } else {
                val intent = Intent()
                val content = binding.edit.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
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

    private fun applyInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}