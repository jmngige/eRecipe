package com.starsolns.erecipe.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.starsolns.erecipe.R
import com.starsolns.erecipe.databinding.FragmentRecipesBinding
import com.starsolns.erecipe.util.Constants.Companion.API_KEY
import com.starsolns.erecipe.util.NetworkResult
import com.starsolns.erecipe.view.adadpter.RecipesAdapter
import com.starsolns.erecipe.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesAdapter: RecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentRecipesBinding.inflate(layoutInflater, container, false)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        recipesAdapter = RecipesAdapter(requireContext())

        setUpRecyclerView()
        getRecipesData()


        return binding.root
    }

    private fun getRecipesData(){
        mainViewModel.getRecipes(recipeQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner){response->
            when(response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let {
                        recipesAdapter.setData(it)
                    }
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    private fun recipeQueries(): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()

        queries["number"] = "30"
        queries["apiKey"] = API_KEY
        queries["type"] = "snack"
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"

        return queries
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