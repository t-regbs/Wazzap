package com.example.wazzap.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wazzap.R
import com.example.wazzap.databinding.FragmentHomeBinding
import com.example.wazzap.model.Post


class HomeFragment : Fragment() {

    private val  homeViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory()
    }

    private lateinit var binding: FragmentHomeBinding
    private val adapter by lazy { FeedAdapter(homeViewModel) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding = FragmentHomeBinding.inflate(inflater).apply {
            lifecycleOwner =  viewLifecycleOwner
            viewModel = homeViewModel
            postsFeed.layoutManager = LinearLayoutManager(requireContext())
            postsFeed.adapter = adapter
            postsFeed.addItemDecoration(divider)
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = HomeFragmentArgs.fromBundle(
            requireArguments()
        )
        Toast.makeText(requireContext(), "Welcome ${args.username}!", Toast.LENGTH_LONG).show()
        binding.addPostFab.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddPostFragment()
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("Check", "blah blash blah")
        listenForPostsUpdates()
    }

    override fun onPause() {
        super.onPause()
        Log.d("Check", "stop blah stop")
        homeViewModel.removePostsValuesChangesListener()
    }

    private fun onPostsUpdate(posts: List<Post>) {
        adapter.submitList(posts)
    }

    private fun listenForPostsUpdates() {
        homeViewModel.onPostsValuesChange().observe(this, Observer(::onPostsUpdate))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.action_logout -> {
            homeViewModel.signout(requireContext())
            findNavController().navigate(R.id.logged_in_to_logged_out)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
            true
        }
    }

}