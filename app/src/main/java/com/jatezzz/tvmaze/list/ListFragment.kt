package com.jatezzz.tvmaze.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jatezzz.tvmaze.R
import com.jatezzz.tvmaze.databinding.FragmentListBinding
import com.jatezzz.tvmaze.list.response.ShowsItem
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: ListViewModel

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var bannerAdapter: ListAdapter

    private val loadingObserver = Observer<Boolean> { isLoading ->
        if (isLoading) {
            Timber.d("LOADING")
        } else {
            Timber.d("STOP LOADING")
        }
    }

    private val listObserver = Observer<List<ShowsItem>> { incomingData ->
        if (incomingData.isNotEmpty()) {
            bannerAdapter.addData(incomingData)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListBinding.bind(view)
        binding.lifecycleOwner = this

        model = ViewModelProvider(this, viewModelFactory)[ListViewModel::class.java]

        model.isLoading.observe(viewLifecycleOwner, loadingObserver)
        model.incomingShowList.observe(viewLifecycleOwner, listObserver)

        bannerAdapter = ListAdapter(requireContext())
        binding.recyclerview.adapter = bannerAdapter
        binding.loadMoreButton.setOnClickListener {
            model.loadMoreShows()
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
