package com.starsolns.erecipe.view.adadpter


import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.starsolns.erecipe.R
import com.starsolns.erecipe.data.room.entities.FavouriteEntity
import com.starsolns.erecipe.databinding.FavouritesItemLayoutBinding
import com.starsolns.erecipe.view.ui.FavouritesFragmentDirections
import com.starsolns.erecipe.viewmodel.MainViewModel

class FavouritesAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
    ) :
    RecyclerView.Adapter<FavouritesAdapter.ViewHolder>(), ActionMode.Callback {

    private var favouritesList = emptyList<FavouriteEntity>()
    private lateinit var mActionMode: ActionMode

    private var multiSelection = false
    private var selectedRecipes = ArrayList<FavouriteEntity>()
    private var viewHolders = ArrayList<ViewHolder>()
    private lateinit var rootView: View

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
        viewHolders.add(holder)
        rootView = holder.itemView.rootView

        holder.itemViewLayout.setOnClickListener {
           if(multiSelection){
              applySelections(holder, currentFavourite)

           }else {
               val action = FavouritesFragmentDirections.actionFavouritesFragmentToDetailsActivity(
                   currentFavourite.result
               )
               holder.itemViewLayout.findNavController().navigate(action)
           }
        }

        holder.itemViewLayout.setOnLongClickListener {
           if(!multiSelection){
               multiSelection = true
               requireActivity.startActionMode(this)
               applySelections(holder, currentFavourite)
               true
           }else {
               applySelections(holder, currentFavourite)
               true
           }
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

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favourites_contextual_menu, menu)
        mActionMode = actionMode!!
        applyStatusBarColor(R.color.contextualModeStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if(menu?.itemId == R.id.delete_favourite_recipes){
           selectedRecipes.forEach { recipes->
               mainViewModel.deleteFavouriteRecipe(recipes)
               showSnackBarMessage("Deleted successfully")
           }

            multiSelection = false
            selectedRecipes.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        applyStatusBarColor(R.color.normalModeStatusBarColor)
        multiSelection = false
        selectedRecipes.clear()
        viewHolders.forEach { holder->
            applySelectionsBackgrounds(holder, R.color.favouritesCardBackground, R.color.favouritesCardStrokeColor)
        }
    }

    private fun showSnackBarMessage(message: String){
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_LONG
        ).setAction("Okay"){}
            .show()
    }

    private fun applyStatusBarColor(color: Int){
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    private fun applySelections(holder: ViewHolder, recipes: FavouriteEntity){
        if(selectedRecipes.contains(recipes)){
            selectedRecipes.remove(recipes)
            applySelectionsBackgrounds(holder, R.color.favouritesCardBackground, R.color.favouritesCardStrokeColor)
            contextualActionBarTitle()
        }else {
            selectedRecipes.add(recipes)
            applySelectionsBackgrounds(holder, R.color.selectedFavouritesCardBackground, R.color.selectedFavouritesCardStrokeColor)
            contextualActionBarTitle()
        }
    }

    private fun applySelectionsBackgrounds(holder: ViewHolder, cardBackground: Int, cardStrokeColor: Int){
        holder.itemViewLayout.setBackgroundColor(ContextCompat.getColor(requireActivity, cardBackground))
        holder.cardViewLayout.strokeColor = ContextCompat.getColor(requireActivity, cardStrokeColor)
    }

    private fun contextualActionBarTitle(){
        when(selectedRecipes.size){
            0 -> {
                mActionMode.finish()
            }
            1 -> {
                mActionMode.title = "${selectedRecipes.size} item"
            }
            else -> {
                mActionMode.title = "${selectedRecipes.size} items"
            }
        }
    }

    fun finishContextualMode(){
        if(this::mActionMode.isInitialized){
            mActionMode.finish()
        }
    }

}


