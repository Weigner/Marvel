package com.weigner.marvel.framework.paging

import androidx.paging.PagingSource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.weigner.core.data.repository.CharactersRemoteDataSource
import com.weigner.core.domain.model.Character
import com.weigner.marvel.factory.response.DataWrapperResponseFactory
import com.weigner.marvel.framework.network.response.DataWrapperResponse
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

@RunWith(MockitoJUnitRunner::class)
class CharactersPagingSourceTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>

    private val dataWrapperResponseFactory = DataWrapperResponseFactory()

    private val characterFactory = CharacterFactory()

    private lateinit var charactersPagingSource: CharactersPagingSource

    @Before
    fun setUp() {
        charactersPagingSource = CharactersPagingSource(remoteDataSource, "")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return a success load result when load is called`() =
        runBlockingTest {
            // Arrange
            whenever(remoteDataSource.fetchCharacters(any())).thenReturn(dataWrapperResponseFactory.create())


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

    @ExperimentalCoroutinesApi
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