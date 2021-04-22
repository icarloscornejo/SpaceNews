package com.prototype.spacenews.presentation.detail

import android.content.Intent
import android.net.Uri
import androidx.annotation.VisibleForTesting
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.prototype.spacenews.R
import com.prototype.spacenews.databinding.DetailFragmentBinding
import com.prototype.spacenews.presentation.util.BaseFragment
import com.prototype.spacenews.presentation.util.setObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<DetailFragmentBinding>(R.layout.detail_fragment) {

    override val viewModel: DetailViewModel by viewModel()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val arguments = navArgs<DetailFragmentArgs>()

    override fun onFragmentReady() {
        viewModel.populateDetails(arguments.value.detail)

        viewModel.backEvent.setObserver(viewLifecycleOwner, ::onBackEvent)
        viewModel.openWebDetailEvent.setObserver(viewLifecycleOwner, ::onOpenWebDetailEvent)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onBackEvent(any: Boolean) = findNavController().popBackStack()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onOpenWebDetailEvent(newsUrl: String) {
        with(Intent(Intent.ACTION_VIEW, Uri.parse(newsUrl))) {
            if (requireActivity().packageManager.resolveActivity(this, 0) != null) {
                startActivity(this)
            }
        }
    }
}