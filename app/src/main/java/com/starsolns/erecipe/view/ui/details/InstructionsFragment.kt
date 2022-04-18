package com.starsolns.erecipe.view.ui.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.starsolns.erecipe.databinding.FragmentInstructionsBinding
import com.starsolns.erecipe.model.Result

class InstructionsFragment : Fragment() {

    private var _binding: FragmentInstructionsBinding? = null
    private val binding get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentInstructionsBinding.inflate(layoutInflater, container, false)

        val args = arguments
        val recipe = args?.getSerializable("recipeDetails") as Result

        binding.instructionsWebview.webViewClient = object: WebViewClient() {}
        val recipeUrl: String = recipe!!.sourceUrl
        binding.instructionsWebview.loadUrl(recipeUrl)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}