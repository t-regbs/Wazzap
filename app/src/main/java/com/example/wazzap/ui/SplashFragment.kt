package com.example.wazzap.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.wazzap.R
import com.example.wazzap.SplashGraphDirections
import com.example.wazzap.firebase.authentication.AuthenticationManager

class SplashFragment: Fragment() {

    private val handler = Handler()
    private val authenticationManager by lazy { AuthenticationManager() }

    private val finishSplash: Runnable = Runnable {
        if (authenticationManager.isUserSignedIn()) {
            Navigation.findNavController(requireView())
                .navigate(
                    SplashGraphDirections.splashToLoggedIn(
                        authenticationManager.getCurrentUser()
                    )
                )
        } else {
            Navigation.findNavController(requireView()).navigate(R.id.splash_to_logged_out)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }


    override fun onStart() {
        super.onStart()
        handler.postDelayed(finishSplash, 1L)
    }

    override fun onStop() {
        handler.removeCallbacks(finishSplash)
        super.onStop()
    }
}