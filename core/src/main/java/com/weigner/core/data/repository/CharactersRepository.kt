package com.weigner.core.data.repository

import androidx.paging.PagingSource
import com.weigner.core.domain.model.Character

interface CharactersRepository {

    fun getCharacters(query: String): PagingSource<Int, Character>
}