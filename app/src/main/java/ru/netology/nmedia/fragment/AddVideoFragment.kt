    package ru.netology.nmedia.fragment

    import android.R.attr.text
    import android.os.Build
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Toast
    import androidx.annotation.RequiresApi
    import androidx.fragment.app.Fragment
    import androidx.fragment.app.viewModels
    import androidx.navigation.fragment.findNavController
    import ru.netology.nmedia.R
    import ru.netology.nmedia.activity.AppActivity.Companion.textArg
    import ru.netology.nmedia.databinding.FragmentAddVideoBinding
    import ru.netology.nmedia.viewmodel.PostViewModel
    import kotlin.getValue

    class AddVideoFragment : Fragment() {
        @RequiresApi(Build.VERSION_CODES.P)
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            val binding = FragmentAddVideoBinding.inflate(layoutInflater, container, false)
            binding.ok.setOnClickListener {
                if (binding.edit.text.isNullOrBlank()) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.post_url_is_empty),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Bundle().apply {
                        textArg = binding.edit.text.toString()
                    }
                    findNavController().navigateUp()
                }
            }
            binding.cancel.setOnClickListener {
                findNavController().navigateUp()
            }
            return binding.root
        }
    }