package com.example.testnewsapp.newsdetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.example.testnewsapp.NewsApplication
import com.example.testnewsapp.databinding.NewsDetailsFragmentBinding
import com.example.testnewsapp.util.setupSnackbar
import javax.inject.Inject

/**
 * @author Abhradeep Ghosh
 */

class NewsDetailFragment : Fragment() {

    @Inject
    lateinit var viewModel: NewsDetailViewModel

    private val args: NewsDetailFragmentArgs by navArgs()

    private lateinit var viewDataBinding: NewsDetailsFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as NewsApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = NewsDetailsFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        viewModel.start(args.articleId)

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)

        viewModel.back.observe(viewLifecycleOwner, {
            findNavController().popBackStack()
        })
    }


}