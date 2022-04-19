package com.starsolns.erecipe.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.starsolns.erecipe.R
import com.starsolns.erecipe.databinding.FragmentFavouritesBinding
import com.starsolns.erecipe.databinding.FragmentRecipesBinding
import com.starsolns.erecipe.view.adadpter.FavouritesAdapter
import com.starsolns.erecipe.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private val favouritesAdapter by lazy {  FavouritesAdapter(requireActivity()) }
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavouritesBinding.inflate(layoutInflater, container, false)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.adapter = favouritesAdapter

        mainViewModel.favouriteRecipes.observe(viewLifecycleOwner){favourites->
            favouritesAdapter.setData(favourites)
        }

        binding.favouritesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.favouritesRv.adapter = favouritesAdapter


        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}