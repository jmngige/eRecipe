package com.starsolns.erecipe.view.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.starsolns.erecipe.R
import com.starsolns.erecipe.databinding.FragmentJokeBinding
import com.starsolns.erecipe.util.Constants.Companion.API_KEY
import com.starsolns.erecipe.util.NetworkResult
import com.starsolns.erecipe.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JokeFragment : Fragment() {

    private var _binding: FragmentJokeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private  var foodJoke: String = "No Joke Found"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJokeBinding.inflate(layoutInflater, container, false)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        mainViewModel.getFoodJoke("519822f169314e83b8038d2b4b4caba3")
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner){response->
            when(response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.materialCardView.visibility = View.VISIBLE
                    binding.foodJokeText.text = response.data!!.text
                    foodJoke = response.data!!.text
                }
                is NetworkResult.Error ->{

                    binding.errorImageView.visibility = View.VISIBLE
                    binding.errorMessages.visibility = View.VISIBLE
                    binding.errorMessages.text = response.message.toString()
                    binding.progressBar.visibility = View.INVISIBLE

                }
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorImageView.visibility = View.INVISIBLE
                    binding.errorMessages.visibility = View.INVISIBLE
                }
            }
        }

        binding.swipeToRefresh.setOnRefreshListener {
           Handler(Looper.getMainLooper()).postDelayed({
               mainViewModel.getFoodJoke("519822f169314e83b8038d2b4b4caba3")
               binding.swipeToRefresh.isRefreshing = false
           },3000)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.food_joke_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.share_joke -> {
                val shareIntent = Intent().apply {
                    this.action = Intent.ACTION_SEND
                    this.type = "text/plain"
                    this.putExtra(Intent.EXTRA_TEXT, foodJoke)
                }
                startActivity(Intent.createChooser(shareIntent, "Share with"))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}