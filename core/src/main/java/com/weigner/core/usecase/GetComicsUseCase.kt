package com.weigner.core.usecase

import com.weigner.core.data.repository.CharactersRepository
import com.weigner.core.domain.model.Comic
import com.weigner.core.usecase.base.ResultStatus
import com.weigner.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetComicsUseCase {

    operator fun invoke(params: GetComicsParams): Flow<ResultStatus<List<Comic>>>

    data class GetComicsParams(val characterId: Int)
}

class GetComicsUseCaseImpl @Inject constructor(
    private val repository: CharactersRepository
) : GetComicsUseCase, UseCase<GetComicsUseCase.GetComicsParams, List<Comic>>() {
    override suspend fun doWork(
        params: GetComicsUseCase.GetComicsParams
    ): ResultStatus<List<Comic>> {
        val comics = repository.getComics(params.characterId)
        return ResultStatus.Success(comics)
    }


}