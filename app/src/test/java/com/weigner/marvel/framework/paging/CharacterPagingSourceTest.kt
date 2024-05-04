package com.weigner.marvel.framework.paging

import androidx.paging.PagingSource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.weigner.core.data.repository.CharactersRemoteDataSource
import com.weigner.core.domain.model.Character
import com.weigner.marvel.factory.response.CharacterPagingFactory
import com.weigner.testing.MainCoroutineRule
import com.weigner.testing.model.CharacterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterPagingSourceTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var remoteDataSource: CharactersRemoteDataSource

    private val characterPagingFactory = CharacterPagingFactory()

    private val characterFactory = CharacterFactory()

    private lateinit var charactersPagingSource: CharactersPagingSource

    @Before
    fun setUp() {
        charactersPagingSource = CharactersPagingSource(remoteDataSource, "")
    }

    @Test
    fun `should return a success load result when load is called`() =
        runBlockingTest {
            // Arrange
            whenever(remoteDataSource.fetchCharacters(any())).thenReturn(characterPagingFactory.create())


            // Act
            val result = charactersPagingSource.load(
                PagingSource.LoadParams.Refresh(null, loadSize = 2, false)
            )


            // Assert
            val expected = listOf(
                characterFactory.create(CharacterFactory.Hero.ThreeDMan),
                characterFactory.create(CharacterFactory.Hero.ABom)
            )

            assertEquals(
                PagingSource.LoadResult.Page(
                    data = expected,
                    prevKey = null,
                    nextKey = 20
                ),
                result
            )
        }

    @Test
    fun `should return a error load result when load is called`() = runBlockingTest {

        val exception = RuntimeException()
        whenever(remoteDataSource.fetchCharacters(any()))
            .thenThrow(exception)

        val result = charactersPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assertEquals(PagingSource.LoadResult.Error<Int, Character>(exception), result)

    }
}