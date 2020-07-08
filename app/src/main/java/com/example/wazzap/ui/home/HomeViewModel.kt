package com.example.wazzap.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wazzap.firebase.authentication.AuthenticationManager
import com.example.wazzap.model.Post
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeViewModel(): ViewModel() {
    private val POSTS_REFERENCE = "posts"
    private val authenticationManager = AuthenticationManager()
    private val postsValues = MutableLiveData<List<Post>>()
    private lateinit var postsValueEventListener: ValueEventListener
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private fun getCurrentTime() = System.currentTimeMillis()

    fun addPost(content: String, onSuccessAction: () -> Unit, onFailureAction: () -> Unit) {
        val postsReference = database.getReference(POSTS_REFERENCE)

        val key = postsReference.push().key ?: ""
        val post = createPost(key, content)

        postsReference.child(key)
            .setValue(post)
            .addOnSuccessListener { onSuccessAction() }
            .addOnFailureListener { onFailureAction() }
    }

    fun onPostsValuesChange(): LiveData<List<Post>> {
        listenForPostsValueChanges()
        return postsValues
    }

    fun removePostsValuesChangesListener() {
        database.getReference(POSTS_REFERENCE).removeEventListener(postsValueEventListener)
    }

    private fun listenForPostsValueChanges() {
        postsValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                // Nothing
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val posts = dataSnapshot.children.mapNotNull { it.getValue(Post::class.java) }.toList()
                    postsValues.postValue(posts)
                } else {
                    postsValues.postValue(emptyList())
                }
            }
        }
        database.getReference(POSTS_REFERENCE).addValueEventListener(postsValueEventListener)
    }

    private fun createPost(key:String, content: String): Post {
        val user = authenticationManager.getCurrentUser()
        val timestamp = getCurrentTime()
        return Post(key, content, user, timestamp)
    }

}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory () : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (HomeViewModel() as T)
}