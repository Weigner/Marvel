package com.weigner.marvel.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weigner.core.domain.model.Comic
import com.weigner.core.usecase.GetComicsUseCase
import com.weigner.core.usecase.base.ResultStatus
import com.weigner.marvel.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getComicsUseCase: GetComicsUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiStates>()
    val uiState: LiveData<UiStates> get() = _uiState
    fun getComics(characterId: Int) = viewModelScope.launch {
        getComicsUseCase(GetComicsUseCase.GetComicsParams(characterId))
            .watchStatus()
    }

    private fun Flow<ResultStatus<List<Comic>>>.watchStatus() = viewModelScope.launch {
        collect { status ->
           _uiState.value = when(status) {
                ResultStatus.Loading -> UiStates.Loading
                is ResultStatus.Success -> {
                    val detailChildVEList = status.data.map {
                        DetailChildVE(it.id, it.imageUrl)
                    }
                    val detailParentVEList = listOf(
                        DetailParentVE(R.string.details_comics_category, detailChildVEList)
                    )

                    UiStates.Success(detailParentVEList)
                }
                is ResultStatus.Error -> UiStates.Error
            }

        }
    }

    sealed class UiStates {
        object Loading : UiStates()
        data class Success(val detailParentList: List<DetailParentVE>) : UiStates()
        object Error : UiStates()
    }
}