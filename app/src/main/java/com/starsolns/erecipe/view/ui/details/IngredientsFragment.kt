package com.starsolns.erecipe.view.ui.details

import android.os.Bundle
import android.util.Log
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

    private val ingredientsAdapter: IngredientsAdapter by lazy { IngredientsAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIngredientsBinding.inflate(layoutInflater, container, false)

        val args = arguments
        val recipe = args?.getSerializable("recipeDetails") as Result

        recipe?.extendedIngredients?.let {
            ingredientsAdapter.setData(it)
            Log.i("TAGO", it.toString())

        }

        Log.i("TAG", "onCreateView: No data Here Too")


        binding.ingredientsRv.layoutManager = LinearLayoutManager(requireActivity())
        binding.ingredientsRv.adapter = ingredientsAdapter


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("TAG", "onCreateView: No data Here Too ooo")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}