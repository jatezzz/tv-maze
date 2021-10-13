package com.jatezzz.tvmaze.episode

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.jatezzz.tvmaze.R
import com.jatezzz.tvmaze.databinding.FragmentEpisodeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EpisodeFragment : Fragment(R.layout.fragment_episode) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: EpisodeViewModel

    private var _binding: FragmentEpisodeBinding? = null
    private val binding get() = _binding!!

    private val loadingObserver = Observer<Boolean> {

    }

    private val dataObserver = Observer<EpisodeViewModel.EpisodeViewData> { data ->
        Glide.with(this)
            .load(data.imageUrl)
            .into(binding.imageViewPoster)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentEpisodeBinding.bind(view)
        binding.lifecycleOwner = this

        model = ViewModelProvider(this, viewModelFactory)[EpisodeViewModel::class.java]

        model.isLoading.observe(viewLifecycleOwner, loadingObserver)
        model.incomingEpisode.observe(viewLifecycleOwner, dataObserver)

        binding.viewmodel = model
        val args: EpisodeFragmentArgs by navArgs()
        model.saveArgs(args.episodeId)
        view.post {
            model.loadDetail()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
