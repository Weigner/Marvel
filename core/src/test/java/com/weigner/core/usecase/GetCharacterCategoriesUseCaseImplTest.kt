package com.weigner.core.usecase

import com.nhaarman.mockitokotlin2.whenever
import com.weigner.core.data.repository.CharactersRepository
import com.weigner.core.usecase.base.ResultStatus
import com.weigner.testing.MainCoroutineRule
import com.weigner.testing.model.CharacterFactory
import com.weigner.testing.model.ComicFactory
import com.weigner.testing.model.EventFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import org.junit.Assert.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCharacterCategoriesUseCaseImplTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var charactersRepository: CharactersRepository

    private lateinit var getCharacterCategoriesUseCase: GetCharacterCategoriesUseCase

    private val character = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)
    private val comics = listOf(ComicFactory().create(ComicFactory.FakeComic.FakeComic1))
    private val events = listOf(EventFactory().create(EventFactory.FakeEvent.FakeEvent1))


    @Before
    fun setUp() {
        getCharacterCategoriesUseCase = GetCharacterCategoriesUseCaseImpl(
            charactersRepository,
            mainCoroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun `should return Success from ResultStatus when get both requests return success`() =
        runTest {
            // arrange
            whenever(charactersRepository.getComics(character.id)).thenReturn(comics)
            whenever(charactersRepository.getEvents(character.id)).thenReturn(events)

            // act
            val result = getCharacterCategoriesUseCase.invoke(GetCharacterCategoriesUseCase.GetCategoriesParams(character.id))

            // assert
            val resultList = result.toList()
            assertEquals(ResultStatus.Loading, resultList[0])
            assertTrue(resultList[1] is ResultStatus.Success)
        }

    @Test
    fun `should return Error from ResultStatus when get events request returns error`() =
        runTest {
            // arrange
            whenever(charactersRepository.getComics(character.id)).thenReturn(comics)
            whenever(charactersRepository.getEvents(character.id)).thenAnswer { throw Throwable() }

            // act
            val result = getCharacterCategoriesUseCase.invoke(GetCharacterCategoriesUseCase.GetCategoriesParams(character.id))

            // assert
            val resultList = result.toList()
            assertEquals(ResultStatus.Loading, resultList[0])
            assertTrue(resultList[1] is ResultStatus.Error)
        }

    @Test
    fun `should return Error from ResultStatus when get comics request returns error`() =
        runTest {
            // arrange
            whenever(charactersRepository.getComics(character.id)).thenAnswer { throw Throwable() }

            // act
            val result = getCharacterCategoriesUseCase.invoke(GetCharacterCategoriesUseCase.GetCategoriesParams(character.id))

            // assert
            val resultList = result.toList()
            assertEquals(ResultStatus.Loading, resultList[0])
            assertTrue(resultList[1] is ResultStatus.Error)
        }
}

