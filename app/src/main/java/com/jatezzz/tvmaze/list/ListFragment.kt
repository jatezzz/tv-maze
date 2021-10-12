package com.jatezzz.tvmaze.list

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jatezzz.tvmaze.R
import com.jatezzz.tvmaze.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: ListViewModel

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var listAdapter: ListAdapter

    private val loadingObserver = Observer<Boolean> {

    }

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

        listAdapter = ListAdapter(requireContext())
        binding.recyclerview.adapter = listAdapter
        binding.loadMoreButton.setOnClickListener {
            model.loadMoreShows()
        }
        binding.searchInput.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER)
        binding.searchInput.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_NULL && event.action == KeyEvent.ACTION_DOWN) {
                model.filterByInput(binding.searchInput.text.toString())
            }
            true
        }

        view.post {
            model.loadShows()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
