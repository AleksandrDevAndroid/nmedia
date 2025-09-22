package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.format.Format
import ru.netology.nmedia.post.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            publisher = "21 мая в 18:36",
            content = """Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу.
        Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам:
        от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила,
        которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать
        цепочку перемен → http://netolo.gy/fyb"""",
            countLiked = 999,
            countShare = 1499,
            countView = 0,
            likeBeMy = false,
            shareBeMy = false

        )


        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyInsets(binding.root)

        with(binding) {
            author.text = post.author
            publisher.text = post.publisher
            content.text = post.content
            countViewShare.text = post.countShare.toString()
            countViewLikes.text = post.countLiked.toString()
            countViewWatch.text = post.countView.toString()

            if (post.likeBeMy) {
                like.setImageResource(R.drawable.sharp_favorite_24)
            }

            like.setOnClickListener {
                post.likeBeMy = !post.likeBeMy
                if (post.likeBeMy) {
                    post.countLiked++
                } else {
                    post.countLiked--
                }
                like.setImageResource(
                    if (post.likeBeMy) R.drawable.baseline_favorite_24
                    else R.drawable.sharp_favorite_24
                )
                binding.countViewLikes.text = Format.format(post.countLiked)
            }
            if (post.shareBeMy) {
                share.setImageResource(R.drawable.baseline_ios_share_24)
            }
            share.setOnClickListener {
                post.shareBeMy = !post.shareBeMy
                post.countShare++
                binding.countViewShare.text = Format.format(post.countShare)
            }
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


