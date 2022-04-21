package com.starsolns.erecipe.view.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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

    private lateinit var mainViewModel: MainViewModel
    private val favouritesAdapter by lazy {  FavouritesAdapter(requireActivity(), mainViewModel) }


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

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favourite_recipes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_all_recipes){
            mainViewModel.deleteAllFavouriteRecipe()
            showSnackBarMessage("All items deleted Successfully")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSnackBarMessage(message: String){
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        ).setAction("Okay"){}
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        favouritesAdapter.finishContextualMode()
    }
}