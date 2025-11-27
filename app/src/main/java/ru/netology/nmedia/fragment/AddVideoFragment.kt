    package ru.netology.nmedia.fragment

    import androidx.activity.OnBackPressedCallback
    import android.os.Build
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.activity.ComponentActivity
    import androidx.annotation.RequiresApi
    import androidx.fragment.app.Fragment
    import androidx.fragment.app.setFragmentResult
    import androidx.navigation.fragment.findNavController
    import ru.netology.nmedia.activity.AppActivity.Companion.textArg
    import ru.netology.nmedia.databinding.FragmentAddVideoBinding
    import ru.netology.nmedia.postRepository.DraftSharedPref



    class AddVideoFragment : Fragment() {
        @RequiresApi(Build.VERSION_CODES.P)
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            val binding = FragmentAddVideoBinding.inflate(layoutInflater, container, false)

            val draft = DraftSharedPref(requireContext())
            val showDraft = draft.readDraft()
            if (!showDraft.isNullOrBlank()) {
                binding.textUrl.setText(showDraft)
            }
            arguments?.textArg?.let(binding.textUrl::setText)



            binding.ok.setOnClickListener {
                val url = binding.textUrl.text.toString()
                if (url.isNullOrBlank()) {
                    draft.clearDraft()
                    findNavController().navigateUp()
                } else {
                    draft.saveDraft(url)
                    setFragmentResult("url", Bundle().apply {
                        textArg = url
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