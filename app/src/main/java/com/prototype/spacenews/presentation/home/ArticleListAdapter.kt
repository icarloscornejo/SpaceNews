package com.prototype.spacenews.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.prototype.domain.model.ArticleModel
import com.prototype.spacenews.R
import com.prototype.spacenews.databinding.ItemArticleBinding
import com.prototype.spacenews.databinding.ItemArticleFeaturedBinding
import com.prototype.spacenews.presentation.util.SingleLiveEvent

class ArticleListAdapter(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val entries: List<ArticleModel>,
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val openArticleDetailEvent: SingleLiveEvent<ArticleModel>
) : RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            if (viewType == FEATURED_VIEW) R.layout.item_article_featured else R.layout.item_article,
            parent,
            false
        )
    )

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) FEATURED_VIEW else NORMAL_VIEW
    }

    override fun getItemCount() = entries.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(entries[position], openArticleDetailEvent)
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(article: ArticleModel, openArticleDetailEvent: SingleLiveEvent<ArticleModel>) {
            with(ArticleViewModel(article, openArticleDetailEvent)) {
                if (article.featured) {
                    DataBindingUtil.bind<ItemArticleFeaturedBinding>(view)?.viewModel = this
                } else {
                    DataBindingUtil.bind<ItemArticleBinding>(view)?.viewModel = this
                }
            }
        }
    }

    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        const val FEATURED_VIEW = 1

        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        const val NORMAL_VIEW = 0
    }
}