package com.example.newsapp

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView

import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.util.Util
import com.example.newsapp.model.Category
import com.example.newsapp.ui.CategoriesFragment
import com.example.newsapp.ui.NewsFragment
import com.example.newsapp.ui.SettingsFragment


class HomeActivity : AppCompatActivity() {

    //private lateinit var appBarConfiguration: AppBarConfiguration
lateinit var drawerLayout:DrawerLayout
lateinit var drawerImage: ImageView
lateinit var categories:LinearLayout
lateinit var settings:LinearLayout
var categoriesFragment:CategoriesFragment= CategoriesFragment()
var settingsFragment:SettingsFragment= SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        showFragment(categoriesFragment)
    }
    fun initViews() {
        drawerLayout = findViewById(R.id.drawer_layout)
        drawerImage = findViewById(R.id.drawer_image)
        categories = findViewById(R.id.categories)
        settings = findViewById(R.id.settings)
        categoriesFragment.onCategoryClicked = object : CategoriesFragment.OnCategoryClicked {
            override fun onCategory(category: Category) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, NewsFragment.getInstance(category))
                    .addToBackStack(null)
                    .commit()
            }

        }
        drawerImage.setOnClickListener {
            drawerLayout.open()
        }
        categories.setOnClickListener {
            showFragment(categoriesFragment)
            drawerLayout.close()
        }
        settings.setOnClickListener {
            showFragment(settingsFragment)
            drawerLayout.close()
        }
    }

  fun showFragment(fragment: Fragment){
      supportFragmentManager.beginTransaction()
          .replace(R.id.fragment_container,fragment)
          .commit()
  }

}