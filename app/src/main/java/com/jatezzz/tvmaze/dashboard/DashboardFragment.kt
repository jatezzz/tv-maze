package com.jatezzz.tvmaze.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jatezzz.tvmaze.R
import com.jatezzz.tvmaze.databinding.FragmentDashboardBinding
import com.jatezzz.tvmaze.favorite.FavoriteFragment
import com.jatezzz.tvmaze.list.ListFragment
import com.jatezzz.tvmaze.peoplelist.PeopleListFragment
import com.jatezzz.tvmaze.settings.SettingsFragment
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
    }

    private fun setupViews() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            openFragment(generateFragmentFromId(menuItem.itemId))
            true
        }
        openFragment(generateFragmentFromId(R.id.action_shows))
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
                FavoriteFragment.newInstance()
            }
            R.id.action_people -> {
                PeopleListFragment.newInstance()
            }
            R.id.action_settings -> {
                SettingsFragment.newInstance()
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
