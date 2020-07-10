package com.example.wazzap.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wazzap.R
import com.example.wazzap.databinding.FragmentLoginBinding
import com.example.wazzap.firebase.authentication.AuthenticationManager

class LoginFragment : Fragment() {

    private val authenticationManager by lazy { AuthenticationManager() }
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.googleSignInButton.setOnClickListener {
            authenticationManager.startSignInFlow(requireActivity())
        }
    }
}