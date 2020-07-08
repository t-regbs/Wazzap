package com.example.wazzap.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wazzap.databinding.PostItemBinding
import com.example.wazzap.model.Post

class FeedAdapter(
    private val homeViewModel: HomeViewModel
): ListAdapter<Post, FeedAdapter.ViewHolder>(FeedDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.fromParent(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        post?.let {
            holder.bind(it,homeViewModel)
        }
    }


    class ViewHolder private constructor(val postItemBinding: PostItemBinding): RecyclerView.ViewHolder(postItemBinding.root) {
        companion object{
            fun fromParent(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PostItemBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }

        fun bind(post: Post, homeViewModel: HomeViewModel){
            postItemBinding.post = post
            postItemBinding.viewModel = homeViewModel
            postItemBinding.executePendingBindings()
        }
    }


}



object FeedDiffCallback: DiffUtil.ItemCallback<Post>(){
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id ==newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem ==newItem
    }

}