package ru.netology.nmedia.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.AppActivity.Companion.textArg
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.postRepository.DraftSharedPref
import ru.netology.nmedia.viewmodel.PostViewModel
import kotlin.getValue


class NewPostFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewPostBinding.inflate(layoutInflater, container, false)
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        var urlString: String? = ""
        parentFragmentManager.setFragmentResultListener("url", viewLifecycleOwner) { _, bundle ->
            urlString = bundle?.getString("textArg")

        }

        arguments?.textArg?.let(binding.edit::setText)

        val draft = DraftSharedPref(requireContext())
        val showDraft = draft.getPref("text")
        if (!showDraft.isNullOrBlank()) {
            binding.edit.setText(showDraft)
        }
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    draft.savePref("text",binding.edit.text.toString())
                    findNavController().navigateUp()
                }
            }
        )

        binding.ok.setOnClickListener {
            if (binding.edit.text.isNullOrBlank()) {
                Toast.makeText(
                    requireContext(), getString(R.string.post_content_is_empty), Toast.LENGTH_SHORT
                ).show()
            } else {
                draft.remove("text")
                draft.remove("url")
                viewModel.save(binding.edit.text.toString(), urlString)
                findNavController().navigateUp()
            }
        }

        binding.cancel.setOnClickListener {
            draft.savePref("text",binding.edit.text.toString())
            findNavController().navigateUp()
        }

        binding.addMedia.setOnClickListener {
            findNavController().navigate(
                R.id.action_newPostFragment_to_addVideoFragment,
            )
        }
        return binding.root
    }
}
