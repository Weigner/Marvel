package com.weigner.marvel.presentation.characters

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.weigner.core.domain.model.Character
import com.weigner.core.domain.model.Episode
import com.weigner.marvel.framework.imageLoader.ImageLoader
import com.weigner.marvel.util.OnCharacterItemClick
import javax.inject.Inject

class CharactersAdapter constructor(
    private val imageLoader: ImageLoader,
    private val onItemClick: OnCharacterItemClick
) : PagingDataAdapter<Episode, CharactersViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder.create(parent, imageLoader, onItemClick)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Episode>() {
            var i = 0
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                i++
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }

        }
    }
}