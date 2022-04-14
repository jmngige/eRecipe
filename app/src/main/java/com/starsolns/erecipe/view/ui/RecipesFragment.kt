package com.starsolns.erecipe.view.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.starsolns.erecipe.R
import com.starsolns.erecipe.databinding.FragmentRecipesBinding
import com.starsolns.erecipe.util.NetworkListener
import com.starsolns.erecipe.util.NetworkResult
import com.starsolns.erecipe.util.observeOnce
import com.starsolns.erecipe.view.adadpter.RecipesAdapter
import com.starsolns.erecipe.viewmodel.MainViewModel
import com.starsolns.erecipe.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var recipesAdapter: RecipesAdapter

    private lateinit var networkListener: NetworkListener

    private val args : RecipesFragmentArgs by navArgs()

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

        binding.floatingActionButton.setOnClickListener {
           if(sharedViewModel.networkStatus){
               findNavController().navigate(R.id.action_recipesFragment_to_bottomSheetFragment)
           }else{
               sharedViewModel.checkNetworkStatus()
           }
        }

//        sharedViewModel.readOnlineStatus.observe(viewLifecycleOwner){status->
//            sharedViewModel.onlineStatus = status
//        }

        /** initialize network listener */
        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkConnectivityStatus(requireContext())
                .collect{status->
                    sharedViewModel.networkStatus = status
                    sharedViewModel.checkNetworkStatus()
                    retrieveRecipesFromDatabase()
                }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    /** retrieve recipes data from database first otherwise if empty retrieve from api */
    private fun retrieveRecipesFromDatabase() {
        lifecycleScope.launch {
            mainViewModel.localRecipes.observeOnce(viewLifecycleOwner){database->
                if(database.isNotEmpty() && !args.fromBottomSheet){
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
                    hideShimmerEffect()
                    response.data?.let {
                        recipesAdapter.setData(it)
                    }
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
                is NetworkResult.Error -> {
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

    /** retrieve recipes from the search query*/
    private fun searchRecipes(query: String){
        mainViewModel.searchRecipe(sharedViewModel.searchRecipesQuery(query))
        mainViewModel.searchRecipeResponse.observe(viewLifecycleOwner){response->
            when(response){
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
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_bar_menu, menu)

        val query = menu.findItem(R.id.nav_search)
        val searchView = query.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchRecipes(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
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