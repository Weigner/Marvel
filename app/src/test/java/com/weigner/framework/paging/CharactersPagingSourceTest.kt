package com.weigner.framework.paging

import androidx.paging.PagingSource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.weigner.core.data.repository.CharactersRemoteDataSource
import com.weigner.core.domain.model.Character
import com.weigner.factory.response.DataWrapperResponseFactory
import com.weigner.marvel.framework.network.response.DataWrapperResponse
import com.weigner.marvel.framework.paging.CharactersPagingSource
import com.weigner.testing.MainCoroutineRule
import com.weigner.testing.model.CharacterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class CharactersPagingSourceTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>

    private val dataWrapperResponseFactory = DataWrapperResponseFactory()

    private val characterFactory = CharacterFactory()

    private lateinit var charactersPAgingSource: CharactersPagingSource

    @Before
    fun setUp() {
        charactersPAgingSource = CharactersPagingSource(remoteDataSource, "")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return a success load result when load is called`() = runBlockingTest {
        // Arrange
        whenever(remoteDataSource.fetchCharacters(any()))
            .thenReturn(dataWrapperResponseFactory.create())

        // Act
        val result = charactersPAgingSource.load(
            PagingSource.LoadParams.Refresh(
                null,
                loadSize = 2,
                false
            )
        )


        // Assert
        val expected = listOf(
            characterFactory.create(CharacterFactory.Hero.ThreeDMan),
            characterFactory.create(CharacterFactory.Hero.ABomb)
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

        // Arrange
        val exception = RuntimeException()
        whenever(remoteDataSource.fetchCharacters(any()))
            .thenThrow(exception)

        // Act
        val result = charactersPAgingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        // Assert
        assertEquals(
            PagingSource.LoadResult.Error<Int, Character>(exception),
            result
        )
    }
}