package com.starsolns.erecipe.view.host

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.starsolns.erecipe.R
import com.starsolns.erecipe.data.room.entities.FavouriteEntity
import com.starsolns.erecipe.databinding.ActivityDetailsBinding
import com.starsolns.erecipe.view.ui.details.IngredientsFragment
import com.starsolns.erecipe.view.ui.details.InstructionsFragment
import com.starsolns.erecipe.view.ui.details.OverviewFragment
import com.starsolns.erecipe.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val args: DetailsActivityArgs by navArgs()
    private lateinit var mainViewModel: MainViewModel

    private var savedRecipe = false
    private var savedRecipeId = 0
    private lateinit var menuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailsToolBar)
        binding.detailsToolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val resultBundle = Bundle()
        resultBundle.putSerializable("recipeDetails", args.result)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val pagerAdapter = com.starsolns.erecipe.view.adadpter.PagerAdapter(
            resultBundle,
            fragments,
            this
        )

        binding.detailsViewpager.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.detailsTabLayout, binding.detailsViewpager){tab, position->
            tab.text = titles[position]
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overview_layout_menu, menu)
        menuItem = menu!!.findItem(R.id.nav_favourite)
        checkSavedRecipes(menuItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }else if(item.itemId == R.id.nav_favourite && !savedRecipe){
            saveFavouriteDatabase(item)
        } else if(item.itemId == R.id.nav_favourite && savedRecipe){
            deleteRecipeFromFavourite(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.favouriteRecipes.observe(this) { favourites ->
                try {
                    for (savedRecipes in favourites){
                      if(savedRecipes.result.id == args.result.id ){
                          changeFavouriteIconColor(menuItem, R.color.yellow)
                          savedRecipeId = savedRecipes.id
                          savedRecipe = true
                      }
                    }

                }catch (e: Exception){
                    Log.i("DetailsActivity", e.message.toString())
                }
        }
    }

    private fun saveFavouriteDatabase(item: MenuItem) {
        val favouriteRecipe = FavouriteEntity(
            savedRecipeId,
            args.result
        )

        mainViewModel.insertFavouriteRecipe(favouriteRecipe)
        changeFavouriteIconColor(menuItem, R.color.yellow)
        showSnackBar("Saved to favourites")
        savedRecipe = true
    }

    private fun deleteRecipeFromFavourite(item: MenuItem) {
        val recipe = FavouriteEntity(
            savedRecipeId,
            args.result
        )

        mainViewModel.deleteFavouriteRecipe(recipe)
        changeFavouriteIconColor(menuItem, R.color.white)
        showSnackBar("Removed from Favourites")
        savedRecipe = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailsLayout,
            message,
            Snackbar.LENGTH_LONG
        )
            .setAction("Okay") {}
            .show()
    }

    private fun changeFavouriteIconColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }

    override fun onDestroy() {
        super.onDestroy()
        changeFavouriteIconColor(menuItem, R.color.yellow)
    }
}