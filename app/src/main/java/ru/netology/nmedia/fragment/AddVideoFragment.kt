    package ru.netology.nmedia.fragment

    import android.os.Build
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Toast
    import androidx.annotation.RequiresApi
    import androidx.fragment.app.Fragment
    import androidx.fragment.app.setFragmentResult
    import androidx.navigation.fragment.findNavController
    import ru.netology.nmedia.R
    import ru.netology.nmedia.activity.AppActivity.Companion.textArg
    import ru.netology.nmedia.databinding.FragmentAddVideoBinding


    class AddVideoFragment : Fragment() {
        @RequiresApi(Build.VERSION_CODES.P)
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            val binding = FragmentAddVideoBinding.inflate(layoutInflater, container, false)
            arguments?.textArg?.let(binding.textUrl::setText)
            binding.ok.setOnClickListener {
                if (binding.textUrl.text.isNullOrBlank()) {
                    findNavController().navigateUp()
                } else {
                    setFragmentResult("url", Bundle().apply {
                        textArg = binding.textUrl.text.toString()
                    })
                    findNavController().navigateUp()
                }
            }
            binding.cancel.setOnClickListener {
                findNavController().navigateUp()
            }
            return binding.root
        }
    }