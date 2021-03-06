package com.jatezzz.tvmaze.favorite

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jatezzz.tvmaze.R
import com.jatezzz.tvmaze.base.BaseFragment
import com.jatezzz.tvmaze.dashboard.DashboardFragmentDirections
import com.jatezzz.tvmaze.databinding.FragmentFavoriteBinding
import com.jatezzz.tvmaze.list.ListAdapter
import com.jatezzz.tvmaze.list.response.Image
import com.jatezzz.tvmaze.list.response.ShowsItem
import com.jatezzz.tvmaze.show.DEFAULT_ID
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: FavoriteViewModel

    private lateinit var listAdapter: ListAdapter

    private val listObserver = Observer<FavoriteViewModel.ViewData> { incomingData ->
        listAdapter.setData(incomingData.shows.map {
            ShowsItem(
                _links = null,
                averageRuntime = null,
                dvdCountry = null,
                ended = null,
                externals = null,
                genres = null,
                id = it.id,
                image = Image(it.imageUrl, it.imageUrl),
                language = null,
                name = it.name,
                network = null,
                officialSite = null,
                premiered = null,
                rating = null,
                runtime = null,
                schedule = null,
                status = null,
                summary = null,
                type = null,
                updated = null,
                url = null,
                webChannel = null,
                weight = null,
            )
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFavoriteBinding.bind(view)
        binding.lifecycleOwner = this

        model = ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]

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
        }, {
            model.remove(it)
        }, true)
        binding.recyclerview.adapter = listAdapter

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    model.filterByInput(s.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Unused but required for object inheritance
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Unused but required for object inheritance
            }
        })
    }

    override fun onStart() {
        super.onStart()
        model.loadShows()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): FavoriteFragment = FavoriteFragment()
    }
}
