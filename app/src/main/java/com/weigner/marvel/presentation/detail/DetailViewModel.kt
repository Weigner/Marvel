package com.weigner.marvel.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weigner.core.domain.model.Comic
import com.weigner.core.usecase.GetComicsUseCase
import com.weigner.core.usecase.GetComicsUseCaseImpl
import com.weigner.core.usecase.base.ResultStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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
                is ResultStatus.Success -> UiStates.Success(status.data)
                is ResultStatus.Error -> UiStates.Error
            }

        }
    }

    sealed class UiStates {
        object Loading : UiStates()
        data class Success(val comics: List<Comic>) : UiStates()
        object Error : UiStates()
    }
}