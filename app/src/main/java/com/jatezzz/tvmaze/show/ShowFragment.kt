package com.jatezzz.tvmaze.show

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jatezzz.tvmaze.R
import com.jatezzz.tvmaze.base.BaseFragment
import com.jatezzz.tvmaze.databinding.FragmentShowBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class ShowFragment : BaseFragment<FragmentShowBinding>(R.layout.fragment_show) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: ShowViewModel

    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var genreAdapter: GenreAdapter

    private val dataObserver = Observer<ShowViewModel.ShowViewData> { data ->
        binding.textViewSummary.text =
            HtmlCompat.fromHtml(data.summary, HtmlCompat.FROM_HTML_MODE_LEGACY)
        Glide.with(this)
            .load(data.imageUrl)
            .into(binding.detailImage)
        genreAdapter.setData(data.genres)

        val sections: MutableList<SimpleSectionedRecyclerViewAdapter.Section> = ArrayList()
        var positionIndex = 0
        for (seasonData in data.episodes) {
            sections.add(
                SimpleSectionedRecyclerViewAdapter.Section(
                    positionIndex,
                    "Season ${seasonData.first}"
                )
            )
            positionIndex += seasonData.second.size
        }
        episodeAdapter.setData(data.episodes.map { it.second }.flatten().toList())
        val mSectionedAdapter = SimpleSectionedRecyclerViewAdapter(
            requireContext(),
            R.layout.section,
            R.id.section_text,
            episodeAdapter
        )
        mSectionedAdapter.setSections(sections.toTypedArray())


        binding.toolbar.setNavigationOnClickListener { _ ->
            findNavController().navigateUp()
        }

        binding.recyclerViewEpisodes.adapter = mSectionedAdapter

        if (data.isFavorite) {
            binding.fab.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_love_heart_symbol,
                    null
                )
            )
        } else {
            binding.fab.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_favorite,
                    null
                )
            )
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentShowBinding.bind(view)
        binding.lifecycleOwner = this

        model = ViewModelProvider(this, viewModelFactory)[ShowViewModel::class.java]

        model.isLoading.observe(viewLifecycleOwner, loadingObserver)
        model.incomingShow.observe(viewLifecycleOwner, dataObserver)


        episodeAdapter = EpisodeAdapter(requireContext(), {
            val action = ShowFragmentDirections.actionShowFragmentToEpisodeFragment(it.id)
            try {
                findNavController().navigate(action)
            } catch (e: Exception) {
                Timber.e(e)
            }
        })
        binding.recyclerViewEpisodes.setHasFixedSize(true)
        binding.recyclerViewEpisodes.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )

        genreAdapter = GenreAdapter()
        binding.recyclerViewGenres.adapter = genreAdapter

        binding.viewmodel = model
        val args: ShowFragmentArgs by navArgs()
        model.saveArgs(args.showId)

        binding.fab.setOnClickListener {
            model.toggleFavorite()
        }

        view.post {
            model.loadDetail()
        }
    }

}
