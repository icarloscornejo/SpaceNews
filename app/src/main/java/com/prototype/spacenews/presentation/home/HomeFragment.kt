package com.prototype.spacenews.presentation.home

import androidx.annotation.VisibleForTesting
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prototype.domain.model.ArticleModel
import com.prototype.domain.model.Resource
import com.prototype.spacenews.R
import com.prototype.spacenews.databinding.HomeFragmentBinding
import com.prototype.spacenews.presentation.util.BaseFragment
import com.prototype.spacenews.presentation.util.setObserver
import com.prototype.spacenews.presentation.util.showSnackbarMessage
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeFragmentBinding>(R.layout.home_fragment) {

    // We override the base viewModel variable by injecting it via Koin
    override val viewModel: HomeViewModel by viewModel()

    override fun onFragmentReady() {
        // Configuring the RecyclerView
        binding.rvArticles.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvArticles.adapter = ArticleListAdapter(
            viewModel.articleList,
            viewModel.openArticleDetailEvent
        )
        // Configuring the SwipeRefreshComponent
        binding.srlArticles.setOnRefreshListener { viewModel.fetchArticles() }

        // Observing ViewModel fields
        viewModel.showSnackbarMessageEvent.setObserver(
            viewLifecycleOwner,
            ::onShowSnackbarMessageEvent
        )
        viewModel.notifyDataChangedEvent.setObserver(
            viewLifecycleOwner,
            ::onNotifyDataChangedEvent
        )
        viewModel.openArticleDetailEvent.setObserver(
            viewLifecycleOwner,
            ::onOpenArticleDetailEvent
        )
        viewModel.articlesData.setObserver(
            viewLifecycleOwner,
            ::onArticlesFetched
        )

        // Fetching articles for the first time
        viewModel.fetchArticles()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onShowSnackbarMessageEvent(props: Pair<String, Int>) {
        requireActivity().showSnackbarMessage(props.first, props.second)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onNotifyDataChangedEvent(any: Boolean) {
        binding.rvArticles.adapter?.notifyDataSetChanged()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onArticlesFetched(resource: Resource<List<ArticleModel>>) {
        viewModel.onArticlesFetched(resource)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onOpenArticleDetailEvent(article: ArticleModel) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(article)
        )
    }
}