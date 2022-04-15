package com.starsolns.erecipe.view.ui.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.starsolns.erecipe.R
import com.starsolns.erecipe.databinding.FragmentOverviewBinding
import com.starsolns.erecipe.model.Result
import org.jsoup.Jsoup

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get()= _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentOverviewBinding.inflate(layoutInflater, container, false)

        val args = arguments
        val recipe = args?.getSerializable("recipeDetails") as Result?

        binding.detailImageView.load(recipe?.image)
        binding.detailRecipeTitle.text = recipe?.title
        binding.detailsLikesText.text = recipe?.aggregateLikes.toString()
        binding.detailsDurationText.text = recipe?.readyInMinutes.toString()
        binding.detailRecipeDescription.text = recipe?.summary


        Log.i("TAG", "onCreateView: No data")


        if(recipe?.vegetarian == true){
            binding.vegeterianCheckIv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.vegeterianCheckTxt.setTextColor(ContextCompat.getColor(
                requireContext(),
                R.color.green
            ))
        }

        if(recipe?.glutenFree  == true){
            binding.vegeterianCheckIv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.vegeterianCheckTxt.setTextColor(ContextCompat.getColor(
                requireContext(),
                R.color.green
            ))
        }

        if(recipe?.veryHealthy == true){
            binding.vegeterianCheckIv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.vegeterianCheckTxt.setTextColor(ContextCompat.getColor(
                requireContext(),
                R.color.green
            ))
        }

        if(recipe?.vegan == true){
            binding.vegeterianCheckIv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.vegeterianCheckTxt.setTextColor(ContextCompat.getColor(
                requireContext(),
                R.color.green
            ))
        }

        if (recipe?.dairyFree == true){
            binding.vegeterianCheckIv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.vegeterianCheckTxt.setTextColor(ContextCompat.getColor(
                requireContext(),
                R.color.green
            ))
        }

        if(recipe?.cheap == true){
            binding.vegeterianCheckIv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.vegeterianCheckTxt.setTextColor(ContextCompat.getColor(
                requireContext(),
                R.color.green
            ))
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}