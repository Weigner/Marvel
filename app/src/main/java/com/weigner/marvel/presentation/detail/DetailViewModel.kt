package com.weigner.marvel.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weigner.core.domain.model.Comic
import com.weigner.core.domain.model.Event
import com.weigner.core.usecase.GetCharacterCategoriesUseCase
import com.weigner.core.usecase.base.ResultStatus
import com.weigner.marvel.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCharacterCategoriesUseCase: GetCharacterCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiStates>()
    val uiState: LiveData<UiStates> get() = _uiState
    fun getCharactersCategories(characterId: Int) = viewModelScope.launch {
        getCharacterCategoriesUseCase(GetCharacterCategoriesUseCase.GetComicsParams(characterId))
            .watchStatus()
    }

    private fun Flow<ResultStatus<Pair<List<Comic>, List<Event>>>>.watchStatus() =
        viewModelScope.launch {
            collect { status ->
                _uiState.value = when (status) {
                    ResultStatus.Loading -> UiStates.Loading
                    is ResultStatus.Success -> {
                        val detailParentList = mutableListOf<DetailParentVE>()

                        val comics = status.data.first
                        if (comics.isNotEmpty()) {
                            comics.map {
                                DetailChildVE(it.id, it.imageUrl)
                            }.also {
                                detailParentList.add(
                                    DetailParentVE(R.string.details_comics_category, it)
                                )
                            }
                        }

                        val events = status.data.second
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
                            UiStates.Success(detailParentList)
                        } else {
                            UiStates.Empty
                        }


                    }

                    is ResultStatus.Error -> UiStates.Error
                }

            }
        }

    sealed class UiStates {
        object Loading : UiStates()
        data class Success(val detailParentList: List<DetailParentVE>) : UiStates()
        object Error : UiStates()
        object Empty : UiStates()
    }
}