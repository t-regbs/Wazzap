package com.example.wazzap.ui.addpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wazzap.firebase.authentication.AuthenticationManager
import com.example.wazzap.model.Post
import com.example.wazzap.ui.home.HomeViewModel
import com.google.firebase.database.FirebaseDatabase

class AddPostViewModel: ViewModel() {
    private val authenticationManager = AuthenticationManager()
    private val POSTS_REFERENCE = "posts"
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun addPost(content: String, onSuccessAction: () -> Unit, onFailureAction: () -> Unit) {
        val postsReference = database.getReference(POSTS_REFERENCE)

        val key = postsReference.push().key ?: ""
        val post = createPost(key, content)

        postsReference.child(key)
            .setValue(post)
            .addOnSuccessListener { onSuccessAction() }
            .addOnFailureListener { onFailureAction() }
    }

    fun getUser() = authenticationManager.getCurrentUser()

    private fun createPost(key:String, content: String): Post {
        val user = authenticationManager.getCurrentUser()
        val timestamp = getCurrentTime()
        return Post(key, content, user, timestamp)
    }

    private fun getCurrentTime() = System.currentTimeMillis()
}

@Suppress("UNCHECKED_CAST")
class AddPostViewModelFactory () : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (AddPostViewModel() as T)
}