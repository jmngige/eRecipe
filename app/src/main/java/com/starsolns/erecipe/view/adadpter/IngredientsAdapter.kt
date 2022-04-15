package com.starsolns.erecipe.view.adadpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.starsolns.erecipe.R
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
       val view = LayoutInflater.from(context).inflate(R.layout.ingredients_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientsAdapter.ViewHolder, position: Int) {
        val currentIngredient = ingredientsList[position]

        holder.title.text = currentIngredient.name
        holder.amount.text = currentIngredient.amount.toString()
        holder.unit.text = currentIngredient.unit
        holder.consistency.text = currentIngredient.consistency
        holder.original.text = currentIngredient.original
        holder.imageView.load(BASE_IMAGE_URL + currentIngredient.image)

    }

    override fun getItemCount(): Int {
       return ingredientsList.size
    }

      class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.ingredient_imageView)
        val title: TextView = itemView.findViewById(R.id.ingredient_name)
        val amount: TextView = itemView.findViewById(R.id.ingredient_amount)
        val unit: TextView = itemView.findViewById(R.id.ingredient_unit)
        val consistency: TextView = itemView.findViewById(R.id.ingredient_consistency)
        val original: TextView = itemView.findViewById(R.id.ingredient_original)

    }

    fun setData(ingredients: List<ExtendedIngredient>){
        val ingredientsDiffUtil = RecipeDiffUtil(ingredientsList, ingredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = ingredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}