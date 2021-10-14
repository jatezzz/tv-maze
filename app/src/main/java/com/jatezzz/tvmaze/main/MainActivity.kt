package com.jatezzz.tvmaze.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.jatezzz.tvmaze.R
import com.jatezzz.tvmaze.common.persistance.KeyType
import com.jatezzz.tvmaze.common.persistance.SecureStorage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: MainViewModel

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        navController = Navigation.findNavController(this, R.id.main_nav_host_fragment)

        val graph = navController
            .navInflater.inflate(R.navigation.main_navigation)
        if (SecureStorage.getString(KeyType.PASSWORD).isNotEmpty()) {
            graph.startDestination = R.id.authenticationFragment
        }
        navController.graph = graph
    }
}