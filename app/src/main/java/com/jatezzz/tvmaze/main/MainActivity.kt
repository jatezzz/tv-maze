package com.jatezzz.tvmaze.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.jatezzz.tvmaze.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.main_nav_host_fragment)
        navController.setGraph(R.navigation.main_navigation)

        model = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val loadingObserver = Observer<Boolean> { isLoading ->
            if (isLoading) {
                Timber.d("LOADING")
            } else {
                Timber.d("STOP LOADING")
            }
        }
        model.isLoading.observe(this, loadingObserver)
        window.decorView.post {
            model.loadShows()
        }
    }
}