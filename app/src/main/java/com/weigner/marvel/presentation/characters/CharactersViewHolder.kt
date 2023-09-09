package com.weigner.marvel.presentation.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.weigner.core.domain.model.Character
import com.weigner.marvel.R
import com.weigner.marvel.databinding.ItemCharacterBinding
import com.weigner.marvel.util.OnCharacterItemClick

class CharactersViewHolder(
    itemCharactersBinding: ItemCharacterBinding,
    private val onItemClick: OnCharacterItemClick
) : RecyclerView.ViewHolder(itemCharactersBinding.root) {

    private val textName = itemCharactersBinding.textName
    private val imageCharacter = itemCharactersBinding.imageCharacter

    fun bind(character: Character) {
        textName.text = character.name
        imageCharacter.transitionName = character.name
        Glide.with(itemView)
            .load(character.imageUrl)
            .fallback(R.drawable.ic_img_loading_error)
            .into(imageCharacter)

        itemView.setOnClickListener {
            onItemClick.invoke(character, imageCharacter)
        }
    }

    companion object {
        fun create(parent: ViewGroup,
                   onItemClick: OnCharacterItemClick): CharactersViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemCharacterBinding.inflate(inflater, parent, false)
            return CharactersViewHolder(itemBinding, onItemClick)
        }
    }
}