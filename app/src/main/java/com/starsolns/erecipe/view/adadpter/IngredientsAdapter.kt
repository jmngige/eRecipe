package com.starsolns.erecipe.view.adadpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.starsolns.erecipe.databinding.IngredientsItemLayoutBinding
import com.starsolns.erecipe.model.ExtendedIngredient
import com.starsolns.erecipe.util.Constants.Companion.BASE_IMAGE_URL

class IngredientsAdapter(
    private val context: Context
): RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientsAdapter.ViewHolder {
        val ingredientLayoutBinding = IngredientsItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(ingredientLayoutBinding)
    }

    override fun onBindViewHolder(holder: IngredientsAdapter.ViewHolder, position: Int) {
        val currentIngredient = ingredientsList[position]
        holder.bind(currentIngredient)


    }

    override fun getItemCount(): Int {
       return ingredientsList.size
    }

     inner class ViewHolder(private val binding: IngredientsItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        private val imageView = binding.ingredientImageView
        private val title = binding.ingredientName
        private val amount = binding.ingredientAmount
        private val unit = binding.ingredientUnit
        private val consistency = binding.ingredientConsistency
        private val original = binding.ingredientOriginal

         fun bind(ingredient: ExtendedIngredient){
             title.text = ingredient.name.capitalize()
             amount.text = ingredient.amount.toString()
             unit.text = ingredient.unit
             consistency.text = ingredient.consistency
             original.text = ingredient.original
             imageView.load(BASE_IMAGE_URL + ingredient.image)

         }
    }

    fun setData(ingredients: List<ExtendedIngredient>){
        val ingredientsDiffUtil = RecipeDiffUtil(ingredientsList, ingredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = ingredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}