package com.example.wazzap

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.wazzap.firebase.authentication.AuthenticationManager
import com.example.wazzap.firebase.authentication.RC_SIGN_IN

class MainActivity : AppCompatActivity() {

    private val authenticationManager by lazy { AuthenticationManager() }
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                navController.navigate(LoggedOutGraphDirections.loggedOutToLoggedIn(authenticationManager.getCurrentUser()))
            } else {
                Toast.makeText(this, getString(R.string.sign_in_fail), Toast.LENGTH_SHORT).show()
            }
        }
    }
}