package com.starsolns.erecipe.view.adadpter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starsolns.erecipe.databinding.RecipeItemLayoutBinding
import com.starsolns.erecipe.model.Recipe
import com.starsolns.erecipe.model.Result

class RecipesAdapter(private val context: Context): RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    private var recipeList = emptyList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val recipeItemLayoutBinding = RecipeItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(recipeItemLayoutBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRecipe = recipeList[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    class ViewHolder(private val binding: RecipeItemLayoutBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(result: Result){
            binding.recipe = result
            binding.executePendingBindings()
        }
    }

    fun setData(newData: Recipe){
        recipeList = newData.results
        notifyDataSetChanged()
    }

}