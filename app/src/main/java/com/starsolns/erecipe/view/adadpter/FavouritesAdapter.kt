package com.starsolns.erecipe.view.adadpter


import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.view.ActionMode
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.starsolns.erecipe.R
import com.starsolns.erecipe.data.room.entities.FavouriteEntity
import com.starsolns.erecipe.databinding.FavouritesItemLayoutBinding
import com.starsolns.erecipe.view.ui.FavouritesFragmentDirections

class FavouritesAdapter(
    private val requireActivity: FragmentActivity,
    ) :
    RecyclerView.Adapter<FavouritesAdapter.ViewHolder>(), android.view.ActionMode.Callback {

    private var favouritesList = emptyList<FavouriteEntity>()
    private lateinit var mActionMode: android.view.ActionMode

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FavouritesItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFavourite = favouritesList[position]
        holder.bind(currentFavourite)
        holder.itemViewLayout.setOnClickListener {
            val action = FavouritesFragmentDirections.actionFavouritesFragmentToDetailsActivity(
                currentFavourite.result
            )
            holder.itemViewLayout.findNavController().navigate(action)
        }

        holder.itemViewLayout.setOnLongClickListener {
            requireActivity.startActionMode(this)
            true
        }
    }

    override fun getItemCount(): Int {
        return favouritesList.size
    }

    fun setData(newList: List<FavouriteEntity>) {
        val diffUtil = RecipeDiffUtil(favouritesList, newList)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        favouritesList = newList
        diffUtilResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: FavouritesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val itemViewLayout = binding.favouritesItemLayout
        val cardViewLayout = binding.favouriteRecipeCardview

        fun bind(favourite: FavouriteEntity) {
            binding.recipe = favourite
            binding.executePendingBindings()
        }

    }

    override fun onCreateActionMode(actionMode: android.view.ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favourites_contextual_menu, menu)
        mActionMode = actionMode!!
        applyStatusBarColor(R.color.contextualModeStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: android.view.ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: android.view.ActionMode?, menu: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(actionMode: android.view.ActionMode?) {
        applyStatusBarColor(R.color.normalModeStatusBarColor)
    }

    private fun applyStatusBarColor(color: Int){
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    fun finishContextualMode(){
        if(this::mActionMode.isInitialized){
            mActionMode.finish()
        }
    }

}


