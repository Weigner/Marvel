package com.weigner.marvel.presentation.detail

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weigner.core.domain.model.Comic
import com.weigner.core.domain.model.Event
import com.weigner.core.usecase.AddFavoriteUseCase
import com.weigner.core.usecase.GetCharacterCategoriesUseCase
import com.weigner.core.usecase.base.CoroutinesDispatchers
import com.weigner.core.usecase.base.ResultStatus
import com.weigner.marvel.R
import com.weigner.marvel.presentation.extentions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCharacterCategoriesUseCase: GetCharacterCategoriesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    coroutinesDispatchers: CoroutinesDispatchers
) : ViewModel() {

    val categories = UiActionStateLiveData(coroutinesDispatchers.main(), getCharacterCategoriesUseCase)

    private val _uiState = MutableLiveData<UiStates>()
    val uiState: LiveData<UiStates> get() = _uiState

    private val _favoriteUiState = MutableLiveData<FavoriteUiStates>()
    val favoriteUiState: LiveData<FavoriteUiStates> get() = _favoriteUiState

    init {
        _favoriteUiState.value = FavoriteUiStates.FavoriteIcon(R.drawable.ic_favorite_unchecked)
    }

    fun getCharactersCategories(characterId: Int) = viewModelScope.launch {
        getCharacterCategoriesUseCase
            .invoke(GetCharacterCategoriesUseCase.GetCategoriesParams(characterId))
            .watchStatus(
                loading = {
                    _uiState.value = UiStates.Loading
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

                    _uiState.value = if (detailParentList.isNotEmpty()) {
                        UiStates.Success(detailParentList)
                    } else {
                        UiStates.Empty
                    }
                },
                error = {
                    _uiState.value = UiStates.Error
                }
            )
    }

    fun updateFavorite(detailViewArg: DetailViewArg) = viewModelScope.launch {
        detailViewArg.run {
            addFavoriteUseCase.invoke(
                AddFavoriteUseCase.Params(characterId, name, imageUrl)
            ).watchStatus(
                loading = {
                    _favoriteUiState.value = FavoriteUiStates.Loading
                },
                success = {
                    _favoriteUiState.value = FavoriteUiStates.FavoriteIcon(R.drawable.ic_favorite_checked)
                },
                error = {}
            )

        }
    }

    sealed class UiStates {
        object Loading : UiStates()
        data class Success(val detailParentList: List<DetailParentVE>) : UiStates()
        object Error : UiStates()
        object Empty : UiStates()
    }

    sealed class FavoriteUiStates {
        object Loading : FavoriteUiStates()
        class FavoriteIcon(@DrawableRes val icon: Int) : FavoriteUiStates()
    }

    companion object {
        const val DELAY = 1000L
    }
}