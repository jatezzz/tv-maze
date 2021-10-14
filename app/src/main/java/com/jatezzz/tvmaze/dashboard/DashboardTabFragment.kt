package com.jatezzz.tvmaze.dashboard

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import timber.log.Timber

abstract class DashboardTabFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

  fun isOnHomeScreen(): Boolean {
    val navHostFragment = childFragmentManager.primaryNavigationFragment as? NavHostFragment
    return try {
      val navController = navHostFragment?.navController
      navController?.graph?.startDestination == navController?.currentDestination?.id
    } catch (error: IllegalStateException) {
      Timber.e(error)
      false
    }
  }

}
