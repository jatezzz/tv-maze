package com.jatezzz.tvmaze.person

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.jatezzz.tvmaze.R
import com.jatezzz.tvmaze.databinding.FragmentPersonBinding
import com.jatezzz.tvmaze.list.ListAdapter
import com.jatezzz.tvmaze.show.DEFAULT_ID
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class PersonFragment : Fragment(R.layout.fragment_person) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: PersonViewModel

    private var _binding: FragmentPersonBinding? = null
    private val binding get() = _binding!!

    private lateinit var listAdapter: ListAdapter

    private val loadingObserver = Observer<Boolean> {

    }

    private val dataObserver = Observer<PersonViewModel.ViewData> { data ->
        Glide.with(this)
            .load(data.imageUrl)
            .into(binding.imageViewPoster)
        binding.textViewName.text = data.name
        listAdapter.setData(data.credits.map { it._embedded.show })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPersonBinding.bind(view)
        binding.lifecycleOwner = this

        model = ViewModelProvider(this, viewModelFactory)[PersonViewModel::class.java]

        model.isLoading.observe(viewLifecycleOwner, loadingObserver)
        model.incomingEpisode.observe(viewLifecycleOwner, dataObserver)

        listAdapter = ListAdapter(requireContext(), {
            val action =
                PersonFragmentDirections.actionPersonFragmentToShowFragment(
                    it.id ?: DEFAULT_ID
                )
            try {
                findNavController().navigate(action)
            } catch (e: Exception) {
                Timber.e(e)
            }
        })
        binding.recyclerview.adapter = listAdapter

        val args: PersonFragmentArgs by navArgs()
        model.saveArgs(args.id)
        view.post {
            model.loadDetail()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
