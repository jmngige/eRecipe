package com.starsolns.erecipe.view.host

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.starsolns.erecipe.R
import com.starsolns.erecipe.databinding.ActivityDetailsBinding
import com.starsolns.erecipe.view.ui.details.IngredientsFragment
import com.starsolns.erecipe.view.ui.details.InstructionsFragment
import com.starsolns.erecipe.view.ui.details.OverviewFragment

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val args: DetailsActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailsToolBar)
        binding.detailsToolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}