package com.starsolns.erecipe.view.adadpter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.starsolns.erecipe.data.room.entities.FavouriteEntity
import com.starsolns.erecipe.databinding.FavouritesItemLayoutBinding
import com.starsolns.erecipe.view.ui.FavouritesFragmentDirections

class FavouritesAdapter(private val context: Context): RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {

    private var favouritesList = emptyList<FavouriteEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FavouritesItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFavourite = favouritesList[position]
        holder.bind(currentFavourite)
        holder.itemViewLayout.setOnClickListener {
            val action = FavouritesFragmentDirections.actionFavouritesFragmentToDetailsActivity(currentFavourite.result)
            holder.itemViewLayout.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return favouritesList.size
    }

    fun setData(newList: List<FavouriteEntity>){
        val diffUtil = RecipeDiffUtil(favouritesList, newList)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        favouritesList = newList
        diffUtilResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: FavouritesItemLayoutBinding): RecyclerView.ViewHolder(binding.root){

        val itemViewLayout = binding.favouritesItemLayout

        fun bind(favourite: FavouriteEntity){
           binding.recipe = favourite
            binding.executePendingBindings()
        }

    }

}