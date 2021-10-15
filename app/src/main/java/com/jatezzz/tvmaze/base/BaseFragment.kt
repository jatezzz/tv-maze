package com.jatezzz.tvmaze.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jatezzz.tvmaze.main.MainActivity

open class BaseFragment<T>(@LayoutRes val contentLayoutId: Int) : Fragment(contentLayoutId) {

    var _binding: T? = null
    val binding get() = _binding!!

    val loadingObserver = Observer<Boolean> {
        getBaseActivity().updateLoading(it)
    }

    private fun getBaseActivity(): MainActivity {
        return this.activity as MainActivity
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
