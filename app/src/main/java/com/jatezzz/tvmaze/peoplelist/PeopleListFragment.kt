package com.jatezzz.tvmaze.peoplelist

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jatezzz.tvmaze.R
import com.jatezzz.tvmaze.dashboard.DashboardFragmentDirections
import com.jatezzz.tvmaze.dashboard.DashboardTabFragment
import com.jatezzz.tvmaze.databinding.FragmentPeopleBinding
import com.jatezzz.tvmaze.peoplelist.response.Person
import com.jatezzz.tvmaze.show.DEFAULT_ID
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class PeopleListFragment : DashboardTabFragment(R.layout.fragment_people) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: PeopleListViewModel

    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!

    private lateinit var peopleListAdapter: PeopleListAdapter

    private val loadingObserver = Observer<Boolean> {

    }

    private val peopleListObserver = Observer<List<Person>> { incomingData ->
        peopleListAdapter.setData(incomingData)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPeopleBinding.bind(view)
        binding.lifecycleOwner = this

        model = ViewModelProvider(this, viewModelFactory)[PeopleListViewModel::class.java]

        model.isLoading.observe(viewLifecycleOwner, loadingObserver)
        model.incomingShowPeopleList.observe(viewLifecycleOwner, peopleListObserver)

        peopleListAdapter = PeopleListAdapter(requireContext(), {
            val action =
                DashboardFragmentDirections.actionDashboardFragmentToPersonFragment(
                    it.id ?: DEFAULT_ID
                )
            try {
                findNavController().navigate(action)
            } catch (e: Exception) {
                Timber.e(e)
            }
        })
        binding.recyclerview.adapter = peopleListAdapter

        binding.searchInput.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER)
        binding.searchInput.setOnEditorActionListener { _, _, _ ->
            model.filterByInput(binding.searchInput.text.toString())
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): PeopleListFragment = PeopleListFragment()
    }
}
