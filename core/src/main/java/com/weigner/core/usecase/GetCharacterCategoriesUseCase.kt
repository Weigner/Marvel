package com.weigner.core.usecase

import com.weigner.core.data.repository.CharactersRepository
import com.weigner.core.domain.model.Comic
import com.weigner.core.domain.model.Event
import com.weigner.core.usecase.base.AppCoroutinesDispatchers
import com.weigner.core.usecase.base.ResultStatus
import com.weigner.core.usecase.base.UseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetCharacterCategoriesUseCase {

    operator fun invoke(params: GetComicsParams): Flow<ResultStatus<Pair<List<Comic>, List<Event>>>>

    data class GetComicsParams(val characterId: Int)
}

class GetCharacterCategoriesUseCaseImpl @Inject constructor(
    private val repository: CharactersRepository,
    private val dispatchers: AppCoroutinesDispatchers
) : GetCharacterCategoriesUseCase,
    UseCase<GetCharacterCategoriesUseCase.GetComicsParams, Pair<List<Comic>, List<Event>>>() {
    override suspend fun doWork(
        params: GetCharacterCategoriesUseCase.GetComicsParams
    ): ResultStatus<Pair<List<Comic>, List<Event>>> {
        return withContext(dispatchers.io) {
            val comicsDeferred = async { repository.getComics(params.characterId) }
            val eventsDeferred = async { repository.getEvents(params.characterId) }

            val comics = comicsDeferred.await()
            val events = eventsDeferred.await()

            ResultStatus.Success(comics to events)
        }

    }


}