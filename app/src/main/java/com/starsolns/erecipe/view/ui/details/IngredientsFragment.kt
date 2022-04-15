package com.starsolns.erecipe.view.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.starsolns.erecipe.R
import com.starsolns.erecipe.databinding.FragmentIngredientsBinding
import com.starsolns.erecipe.databinding.FragmentOverviewBinding
import com.starsolns.erecipe.model.Result
import com.starsolns.erecipe.view.adadpter.IngredientsAdapter

class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get()= _binding!!

    private lateinit var ingredientsAdapter: IngredientsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIngredientsBinding.inflate(layoutInflater, container, false)

        ingredientsAdapter = IngredientsAdapter(requireContext())

        val args = arguments
        val recipe = args?.getSerializable("recipeDetails") as Result

        recipe?.extendedIngredients?.let {
            ingredientsAdapter.setData(it)
        }

        binding.ingredientsRv.layoutManager = LinearLayoutManager(requireActivity())
        binding.ingredientsRv.adapter = ingredientsAdapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}