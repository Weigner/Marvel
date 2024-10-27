package com.weigner.marvel.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.weigner.core.usecase.GetCharacterCategoriesUseCase
import com.weigner.core.usecase.base.ResultStatus
import com.weigner.marvel.R
import com.weigner.testing.MainCoroutineRule
import com.weigner.testing.model.CharacterFactory
import com.weigner.testing.model.ComicFactory
import com.weigner.testing.model.EventFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var getCharacterCategoriesUseCase: GetCharacterCategoriesUseCase

    @Mock
    private lateinit var uiStateObserve: Observer<DetailViewModel.UiStates>

    private lateinit var detailViewModel: DetailViewModel

    private val character = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)
    private val comics = listOf(ComicFactory().create(ComicFactory.FakeComic.FakeComic1))
    private val events = listOf(EventFactory().create(EventFactory.FakeEvent.FakeEvent1))

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(getCharacterCategoriesUseCase)
        detailViewModel.uiState.observeForever(uiStateObserve)
    }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns success`() {
        return runTest {
            //Arrange
            whenever(getCharacterCategoriesUseCase.invoke(any()))
                .thenReturn(flowOf(ResultStatus.Success(comics to events)))
            //Act
            detailViewModel.getCharactersCategories(character.id)

            //Assert
            verify(uiStateObserve).onChanged(isA<DetailViewModel.UiStates.Success>())

            val uiStateSuccess = detailViewModel.uiState.value as DetailViewModel.UiStates.Success
            val categoriesParentList = uiStateSuccess.detailParentList

            assertEquals(2, categoriesParentList.size)
            assertEquals(R.string.details_comics_category, categoriesParentList[0].categoryStringResId)
            assertEquals(R.string.details_events_category, categoriesParentList[1].categoryStringResId)
        }
    }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns only comics`() {
        return runTest {
            //Arrange
            whenever(getCharacterCategoriesUseCase.invoke(any()))
                .thenReturn(flowOf(ResultStatus.Success(comics to emptyList())))
            //Act
            detailViewModel.getCharactersCategories(character.id)

            //Assert
            verify(uiStateObserve).onChanged(isA<DetailViewModel.UiStates.Success>())

            val uiStateSuccess = detailViewModel.uiState.value as DetailViewModel.UiStates.Success
            val categoriesParentList = uiStateSuccess.detailParentList

            assertEquals(1, categoriesParentList.size)
            assertEquals(R.string.details_comics_category, categoriesParentList[0].categoryStringResId)
        }
    }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns only events`() {
        // TODO: Implement tests
    }

    @Test
    fun `should notify uiState with Empty from UiState when get character categories returns an empty result list`() {
        // TODO: Implement tests
    }

    @Test
    fun `should notify uiState with Error from UiState when get character categories returns an exception`() {
        // TODO: Implement tests
    }
}