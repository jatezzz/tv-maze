package com.jatezzz.tvmaze.main

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.jatezzz.tvmaze.R
import com.jatezzz.tvmaze.common.persistance.KeyType
import com.jatezzz.tvmaze.common.persistance.SecureStorage
import com.jatezzz.tvmaze.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: MainViewModel

    private lateinit var navController: NavController

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        navController = Navigation.findNavController(this, R.id.main_nav_host_fragment)

        val graph = navController
            .navInflater.inflate(R.navigation.main_navigation)
        if (SecureStorage.getString(KeyType.PASSWORD).isNotEmpty()) {
            graph.startDestination = R.id.authenticationFragment
        }
        navController.graph = graph
    }

    fun updateLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this)
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}