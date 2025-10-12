package com.weigner.marvel.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.weigner.core.usecase.GetCharacterCategoriesUseCase
import com.weigner.core.usecase.GetCharactersUseCase
import com.weigner.marvel.R
import com.weigner.marvel.presentation.extentions.watchStatus
import kotlin.coroutines.CoroutineContext

class UiActionStateLiveData(
    private val coroutineContext: CoroutineContext,
    private val getCharacterCategoriesUseCase: GetCharacterCategoriesUseCase
) {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiStates> = action.switchMap {
        liveData(coroutineContext) {
            when(it) {
                is Action.Load -> {
                    getCharacterCategoriesUseCase
                        .invoke(GetCharacterCategoriesUseCase.GetCategoriesParams(it.characterId))
                        .watchStatus(
                            loading = {
                                emit(UiStates.Loading)
                            },
                            success = { data ->
                                val detailParentList = mutableListOf<DetailParentVE>()

                                val comics = data.first
                                if (comics.isNotEmpty()) {
                                    comics.map {
                                        DetailChildVE(it.id, it.imageUrl)
                                    }.also {
                                        detailParentList.add(
                                            DetailParentVE(R.string.details_comics_category, it)
                                        )
                                    }
                                }

                                val events = data.second
                                if (events.isNotEmpty()) {
                                    events.map {
                                        DetailChildVE(it.id, it.imageUrl)
                                    }.also {
                                        detailParentList.add(
                                            DetailParentVE(R.string.details_events_category, it)
                                        )
                                    }
                                }

                                if (detailParentList.isNotEmpty()) {
                                    emit(UiStates.Success(detailParentList))
                                } else {
                                    emit(UiStates.Empty)
                                }
                            },
                            error = {
                                emit(UiStates.Error)
                            }
                        )
                }
            }
        }
    }

    fun load(characterId: Int) {
        action.value = Action.Load(characterId)
    }

    sealed class UiStates {
        object Loading : UiStates()
        data class Success(val detailParentList: List<DetailParentVE>) : UiStates()
        object Error : UiStates()
        object Empty : UiStates()
    }

    sealed class Action {
        data class Load(val characterId: Int): Action()
    }
}