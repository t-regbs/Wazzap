package com.example.wazzap.ui.addpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.wazzap.R
import com.example.wazzap.databinding.FragmentAddPostBinding
import com.example.wazzap.util.showToast

class AddPostFragment : Fragment() {

    private val addPostViewModel by viewModels<AddPostViewModel> {
        AddPostViewModelFactory()
    }
    private lateinit var binding: FragmentAddPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setupClickListeners()
        focusPostMessageInput()
    }

    private fun setupClickListeners() {
        binding.addPostButton.setOnClickListener {
            addPostIfNotEmpty()
        }
    }

    private fun addPostIfNotEmpty() {
        val postMessage = binding.postText.editText?.text.toString().trim()
        if (postMessage.isNotEmpty()) {
            addPostViewModel.addPost(
                postMessage,
                {showToast(getString(R.string.posted_successfully))},
                {showToast(getString(R.string.posting_failed))})
            val action = AddPostFragmentDirections.actionAddPostFragmentToHomeFragment(addPostViewModel.getUser())
            findNavController().navigate(action)
        } else {
            showToast(getString(R.string.empty_post_message))
        }
    }

    private fun focusPostMessageInput() {
        binding.postText.requestFocus()
    }
}