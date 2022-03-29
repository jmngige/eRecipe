package com.starsolns.erecipe.view.adadpter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.starsolns.erecipe.R

class RecipeBindingAdapter {

    companion object {

        @BindingAdapter("android:loadImage")
        @JvmStatic
        fun loadImage(imageView: ImageView, imageUrl: String){
            imageView.load(imageUrl){
                crossfade(600)
            }
        }

        @BindingAdapter("android:setLikesCount")
        @JvmStatic
        fun setLikesCount(textview: TextView, count: Int){
            textview.text = count.toString()
        }

        @BindingAdapter("android:setCookingDuration")
        @JvmStatic
        fun setCookingDuration(textview: TextView, duration: Int){
            textview.text = duration.toString()
        }

        @BindingAdapter("android:defineVeganState")
        @JvmStatic
        fun defineVeganState(view: View, vegan: Boolean){
            if (vegan){
                when(view){
                    is ImageView ->{
                        view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                    }
                    is TextView -> {
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                    }
                }
            }
        }


    }

}