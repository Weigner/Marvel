package com.weigner.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.weigner.core.data.repository.EpisodesRepository
import com.weigner.core.domain.model.Episode
import com.weigner.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val charactersRepository: EpisodesRepository
) : PagingUseCase<GetCharactersUseCase.GetCharactersParams, Episode>() {

    override fun createFlowObservable(params: GetCharactersParams): Flow<PagingData<Episode>> {
        return Pager(config = params.pagingConfig) {
            charactersRepository.getEpisodes()
        }.flow
    }

    data class GetCharactersParams(val pagingConfig: PagingConfig)
}