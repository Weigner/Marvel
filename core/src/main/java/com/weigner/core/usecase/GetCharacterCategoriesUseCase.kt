package com.weigner.core.usecase

import com.weigner.core.data.repository.EpisodesRepository
import com.weigner.core.domain.model.Episode
import com.weigner.core.usecase.base.CoroutinesDispatchers
import com.weigner.core.usecase.base.ResultStatus
import com.weigner.core.usecase.base.UseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetCharacterCategoriesUseCase {

    operator fun invoke(params: GetCategoriesParams): Flow<ResultStatus<Pair<List<Episode>, List<Episode>>>>

    data class GetCategoriesParams(val characterId: Int)
}

class GetCharacterCategoriesUseCaseImpl @Inject constructor(
    private val repository: EpisodesRepository,
    private val dispatchers: CoroutinesDispatchers
) : GetCharacterCategoriesUseCase,
    UseCase<GetCharacterCategoriesUseCase.GetCategoriesParams, Pair<List<Episode>, List<Episode>>>() {
    override suspend fun doWork(
        params: GetCharacterCategoriesUseCase.GetCategoriesParams
    ): ResultStatus<Pair<List<Episode>, List<Episode>>> {
        return withContext(dispatchers.io()) {
            val comicsDeferred = async { repository.getEpisodes(listOf(valor1, valor2, valor3)) }
            val eventsDeferred = async { repository.getEpisodes(listOf(valor4, valor5, valor6)) }

            val comics = comicsDeferred.await()
            val events = eventsDeferred.await()

            ResultStatus.Success(comics to events)
        }

    }

    companion object {
        const val valor1 = 1
        const val valor2 = 2
        const val valor3 = 3
        const val valor4 = 4
        const val valor5 = 5
        const val valor6 = 6
    }


}