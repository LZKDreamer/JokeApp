package com.lzk.jokeapp

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.lzk.jokeapp.ext.navigation.NavGraphBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        NavGraphBuilder.build(this,navController,nav_host_fragment.childFragmentManager,R.id.nav_host_fragment)
        navView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navController.navigate(item.itemId)
        return item.title.isNotEmpty()
    }
}