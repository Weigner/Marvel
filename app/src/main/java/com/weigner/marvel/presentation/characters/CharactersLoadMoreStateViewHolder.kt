package com.weigner.marvel.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.weigner.marvel.databinding.ItemCharactersLoadMoreStateBinding

class CharactersLoadMoreStateViewHolder(
    itemBinding: ItemCharactersLoadMoreStateBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    private val binding = ItemCharactersLoadMoreStateBinding.bind(itemView)
    private val progressBarLoadingMore = binding.progressLoadMore
    private val textTryAgain = binding.textTryAgain.also {
        it.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) {
        progressBarLoadingMore.isVisible = loadState is LoadState.Loading
        textTryAgain.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): CharactersLoadMoreStateViewHolder {
            val itemBinding = ItemCharactersLoadMoreStateBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return CharactersLoadMoreStateViewHolder(itemBinding, retry)
        }
    }
}