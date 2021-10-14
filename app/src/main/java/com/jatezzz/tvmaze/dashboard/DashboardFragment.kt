package com.jatezzz.tvmaze.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jatezzz.tvmaze.R
import com.jatezzz.tvmaze.databinding.FragmentDashboardBinding
import com.jatezzz.tvmaze.list.ListFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: DashboardViewModel

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDashboardBinding.bind(view)
        binding.lifecycleOwner = this

        model = ViewModelProvider(this, viewModelFactory)[DashboardViewModel::class.java]
        setupViews()
        view.post {

        }
    }


    private fun setupViews() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            val isOnDifferentTabs = binding.bottomNavigationView.selectedItemId != menuItem.itemId
            if (!isNavigationRejected(isOnDifferentTabs)) {
                openFragment(generateFragmentFromId(menuItem.itemId))
            }
            true
        }
        openFragment(generateFragmentFromId(R.id.action_shows))
    }


    private fun isNavigationRejected(isOnDifferentTabs: Boolean): Boolean {
        if (isOnDifferentTabs) {
            return false
        }
        return retrieveCurrentShownFragment()?.isOnHomeScreen() ?: false
    }

    private fun retrieveCurrentShownFragment(): DashboardTabFragment? {
        val filteredFragments =
            childFragmentManager.fragments.filterIsInstance<DashboardTabFragment>()
        return filteredFragments.takeIf { it.isNotEmpty() }?.first()
    }

    private fun openFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(R.id.main_container_view, fragment).commit()
    }


    private fun generateFragmentFromId(itemId: Int): Fragment {
        return when (itemId) {
            R.id.action_shows -> {
                ListFragment.newInstance()
            }
            R.id.action_favorites -> {
                ListFragment.newInstance()
            }
            R.id.action_people -> {
                ListFragment.newInstance()
            }
            else -> {
                throw IllegalStateException("ILLEGAL_FRAGMENT_ERROR_MESSAGE")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}