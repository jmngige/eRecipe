package com.starsolns.erecipe.view.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.starsolns.erecipe.R
import com.starsolns.erecipe.databinding.FragmentRecipesBinding
import com.starsolns.erecipe.util.Constants
import com.starsolns.erecipe.util.Constants.Companion.API_KEY
import com.starsolns.erecipe.util.NetworkResult
import com.starsolns.erecipe.view.adadpter.RecipesAdapter
import com.starsolns.erecipe.viewmodel.MainViewModel
import com.starsolns.erecipe.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var recipesAdapter: RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        recipesAdapter = RecipesAdapter(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentRecipesBinding.inflate(layoutInflater, container, false)

        setUpRecyclerView()
        retrieveRecipesFromDatabase()


        return binding.root
    }

    /** retrieve recipes data from database first otherwise if empty retrieve from api */
    private fun retrieveRecipesFromDatabase() {
        lifecycleScope.launch {
            mainViewModel.localRecipes.observe(viewLifecycleOwner){database->
                if(database.isNotEmpty()){
                    recipesAdapter.setData(database[0].recipe)
                    hideShimmerEffect()
                }else {
                    getRemoteRecipesData()
                }
            }
        }
    }

    /** retrieve data from the remote api */
    private fun getRemoteRecipesData(){
        mainViewModel.getRecipes(sharedViewModel.recipeQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    Log.i("RecipeFragment", "retrieved recipes")
                    hideShimmerEffect()
                    response.data?.let {
                        Log.i("RecipeFragment", "display recipes")
                        recipesAdapter.setData(it)
                    }
                }
                is NetworkResult.Loading -> {
                    Log.i("RecipeFragment", "Loading recipes")
                    showShimmerEffect()
                }
                is NetworkResult.Error -> {
                    Log.i("RecipeFragment", "Network Error")
                    cachedRecipes()
                    hideShimmerEffect()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }

        }
    }

    /** retrieve cached data in the database */
    private fun cachedRecipes(){
        lifecycleScope.launch {
            mainViewModel.localRecipes.observe(viewLifecycleOwner){database->
                if(database.isNotEmpty()){
                    recipesAdapter.setData(database[0].recipe)
                }
            }
        }
    }

    private fun setUpRecyclerView(){
        binding.recipesRv.adapter = recipesAdapter
        binding.recipesRv.layoutManager = LinearLayoutManager(requireActivity())
        showShimmerEffect()
    }

    private fun showShimmerEffect(){
        binding.recipesRv.showShimmer()
    }

    private fun hideShimmerEffect(){
        binding.recipesRv.hideShimmer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}