package com.example.wazzap.util

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
fun Activity.showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()