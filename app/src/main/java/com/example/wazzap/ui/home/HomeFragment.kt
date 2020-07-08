package com.example.wazzap.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.wazzap.R
import com.example.wazzap.databinding.FragmentHomeBinding
import com.example.wazzap.ui.HomeFragmentArgs


class HomeFragment : Fragment() {

    private val  homeViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory()
    }

    private lateinit var binding: FragmentHomeBinding
    private  lateinit var adapter: FeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = HomeFragmentArgs.fromBundle(
            requireArguments()
        )
        Toast.makeText(requireContext(), "Welcome ${args.username}!", Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.action_logout -> {
            findNavController().navigate(R.id.logged_in_to_logged_out)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
            true
        }
    }

}