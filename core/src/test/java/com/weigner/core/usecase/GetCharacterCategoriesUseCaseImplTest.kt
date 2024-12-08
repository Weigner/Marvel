package com.weigner.core.usecase

import com.weigner.core.data.repository.CharactersRepository
import com.weigner.testing.MainCoroutineRule
import com.weigner.testing.model.CharacterFactory
import com.weigner.testing.model.ComicFactory
import com.weigner.testing.model.EventFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
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

    private val comics = listOf(ComicFactory().create(ComicFactory.FakeComic.FakeComic1))
    private val events = listOf(EventFactory().create(EventFactory.FakeEvent.FakeEvent1))


    @Before
    fun setUp() {
        getCharacterCategoriesUseCase = GetCharacterCategoriesUseCaseImpl(charactersRepository, mainCoroutineRule.testDispatcherProvider)
    }

}