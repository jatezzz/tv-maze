package com.jatezzz.tvmaze.list

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jatezzz.tvmaze.R
import com.jatezzz.tvmaze.base.BaseFragment
import com.jatezzz.tvmaze.dashboard.DashboardFragmentDirections
import com.jatezzz.tvmaze.databinding.FragmentListBinding
import com.jatezzz.tvmaze.show.DEFAULT_ID
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(R.layout.fragment_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: ListViewModel

    private lateinit var listAdapter: ListAdapter

    private val listObserver = Observer<ListViewModel.ListViewData> { incomingData ->
        if (incomingData.hasAppendableData) {
            listAdapter.addData(incomingData.shows)
            binding.loadMoreButton.visibility = View.VISIBLE
        } else {
            listAdapter.setData(incomingData.shows)
            binding.loadMoreButton.visibility = View.GONE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListBinding.bind(view)
        binding.lifecycleOwner = this

        model = ViewModelProvider(this, viewModelFactory)[ListViewModel::class.java]

        model.isLoading.observe(viewLifecycleOwner, loadingObserver)
        model.incomingShowList.observe(viewLifecycleOwner, listObserver)

        listAdapter = ListAdapter(requireContext(), {
            val action =
                DashboardFragmentDirections.actionDashboardFragmentToShowFragment(
                    it.id ?: DEFAULT_ID
                )
            try {
                findNavController().navigate(action)
            } catch (e: Exception) {
                Timber.e(e)
            }
        })
        binding.recyclerview.adapter = listAdapter
        binding.loadMoreButton.setOnClickListener {
            model.loadMoreShows()
        }
        binding.searchInput.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER)
        binding.searchInput.setOnEditorActionListener { _, _, _ ->
            model.filterByInput(binding.searchInput.text.toString())
            true
        }
    }

    override fun onStart() {
        super.onStart()
        model.loadShows()
    }

    companion object {
        fun newInstance(): ListFragment = ListFragment()
    }
}
