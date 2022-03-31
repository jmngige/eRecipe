package com.starsolns.erecipe.view.adadpter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.starsolns.erecipe.data.room.RecipeEntity
import com.starsolns.erecipe.model.Recipe
import com.starsolns.erecipe.util.NetworkResult

class RecipeBindingAdapter {

    companion object {

        @BindingAdapter("android:readApiResponse", "android:readCacheResponse", requireAll = true)
        @JvmStatic
        fun showErrorImageView(
            imageView: ImageView,
            apiResponse: NetworkResult<Recipe>,
            cacheRecipes: List<RecipeEntity>
        ){
            if(apiResponse is NetworkResult.Error && cacheRecipes.isNullOrEmpty()){
                imageView.visibility = View.VISIBLE
            }else if(apiResponse is NetworkResult.Loading){
                imageView.visibility = View.INVISIBLE
            }else if (apiResponse is NetworkResult.Success){
                imageView.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:readApiResponse2", "android:readCacheResponse2", requireAll = true)
        @JvmStatic
        fun showErrorImageView(
            textView: TextView,
            apiResponse: NetworkResult<Recipe>,
            cacheRecipes: List<RecipeEntity>
        ){
            if(apiResponse is NetworkResult.Error && cacheRecipes.isNullOrEmpty()){
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            }else if(apiResponse is NetworkResult.Loading){
                textView.visibility = View.INVISIBLE
            }else if (apiResponse is NetworkResult.Success){
                textView.visibility = View.INVISIBLE
            }
        }


    }

}