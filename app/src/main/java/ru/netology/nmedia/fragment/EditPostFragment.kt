    package ru.netology.nmedia.fragment

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
    import ru.netology.nmedia.activity.AppActivity.Companion.longArg
    import ru.netology.nmedia.activity.AppActivity.Companion.textArg
    import ru.netology.nmedia.databinding.FragmentNewPostBinding
    import ru.netology.nmedia.viewmodel.PostViewModel
    import kotlin.getValue


    class EditPostFragment : Fragment() {
        @RequiresApi(Build.VERSION_CODES.P)
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val binding = FragmentNewPostBinding.inflate(layoutInflater, container, false)
            val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

            arguments?.textArg?.let(binding.edit::setText)
            val postID = arguments?.longArg

            binding.ok.setOnClickListener {
                if (binding.edit.text.isNullOrBlank()) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.post_content_is_empty),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    viewModel.edit(postID,binding.edit.text.toString())
                    findNavController().navigateUp()
                }
            }
            binding.cancel.setOnClickListener {
                findNavController().navigateUp()
            }
            return binding.root
        }
    }
