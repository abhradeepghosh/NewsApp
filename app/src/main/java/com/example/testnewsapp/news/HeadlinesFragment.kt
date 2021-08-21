package com.example.testnewsapp.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.example.testnewsapp.NewsApplication
import com.example.testnewsapp.databinding.HeadlinesFragmentBinding
import com.example.testnewsapp.util.EventObserver
import com.example.testnewsapp.util.setupRefreshLayout
import com.example.testnewsapp.util.setupSnackbar
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Abhradeep Ghosh
 */

class HeadlinesFragment : Fragment() {

    @Inject
    lateinit var viewModel: HeadlinesViewModel

    private lateinit var viewDataBinding: HeadlinesFragmentBinding

    private lateinit var listAdapter: HeadlinesAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as NewsApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = HeadlinesFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupSnackbar()
        setupListAdapter()
        setupRefreshLayout(viewDataBinding.refreshLayout, viewDataBinding.recyclerViewHeadlines)
        setupNavigation()
        viewModel.items.observe(viewLifecycleOwner, {
            listAdapter.submitList(it)
        })
    }

    /**
     * Setup snackbar and show message in snackbar
     */
    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    /**
     * Setup navigation to navigate from news list to news details screen
     */
    private fun setupNavigation() {
        viewModel.openArticleEvent.observe(viewLifecycleOwner, EventObserver {
            openNewsDetails(it)
        })
    }


    /**
     * Passing the action to navController to navigate from news list to news details screen
     */
    private fun openNewsDetails(articleId: Int) {
        val action =
            HeadlinesFragmentDirections.actionHeadlinesFragmentToNewsDetailFragment(articleId)
        findNavController().navigate(action)
    }

    /**
     * Adapter setup for population of news data.
     */
    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = HeadlinesAdapter(viewModel)
            viewDataBinding.recyclerViewHeadlines.adapter = listAdapter
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }

    }
}