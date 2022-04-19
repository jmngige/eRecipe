package com.starsolns.erecipe.view.adadpter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.starsolns.erecipe.data.room.entities.FavouriteEntity

class FavouritesBindingAdapter {

    companion object {

        @BindingAdapter("setVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setViewsVisibility(view: View, favourites: List<FavouriteEntity>?, adapter: FavouritesAdapter){

            if(favourites.isNullOrEmpty()){
                when(view){
                    is ImageView ->{
                        view.visibility = View.VISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.VISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.INVISIBLE
                    }
                }
            }else {
                when(view){
                    is ImageView ->{
                        view.visibility = View.INVISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.VISIBLE
                        adapter?.setData(favourites)
                    }
                }
            }

        }

    }

}