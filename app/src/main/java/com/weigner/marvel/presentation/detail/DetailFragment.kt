package com.weigner.marvel.presentation.detail

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.weigner.marvel.databinding.FragmentDetailBinding
import com.weigner.marvel.framework.imageLoader.ImageLoader
import com.weigner.marvel.presentation.extentions.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailBinding.inflate(inflater, container, false)
            .apply { _binding = this }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailViewArg = args.detailViewArg
        binding.imageCharacter.run {
            transitionName = detailViewArg.name
            imageLoader.load(this, detailViewArg.imageUrl)
        }

        setSharedElementTransitionOnEnter()

        loadCategoriesAndObserveUiState(detailViewArg)
        setAndObserveFavoriteUiState(detailViewArg)

        binding.flipperFavorite.displayedChild = FLIPPER_FAVORITE_CHILD_POSITION_LOADING
    }

    private fun loadCategoriesAndObserveUiState(detailViewArg: DetailViewArg) {
        viewModel.categories.load(detailViewArg.characterId)
        viewModel.categories.state.observe(viewLifecycleOwner) { uiState ->
            binding.flipperDetail.displayedChild = when(uiState) {
                UiActionStateLiveData.UiStates.Loading -> FLIPPER_CHILD_POSITION_LOADING
                is UiActionStateLiveData.UiStates.Success -> {
                    binding.recyclerParentDetail.run {
                        setHasFixedSize(true)
                        adapter = DetailParentAdapter(uiState.detailParentList, imageLoader)
                    }
                    FLIPPER_CHILD_POSITION_DETAIL
                }
                UiActionStateLiveData.UiStates.Error -> {
                    binding.includeErrorView.buttonRetry.setOnClickListener {
                        viewModel.categories.load(detailViewArg.characterId)
                    }
                    FLIPPER_CHILD_POSITION_ERROR
                }
                UiActionStateLiveData.UiStates.Empty -> FLIPPER_CHILD_POSITION_EMPTY
            }
        }
    }

    private fun setAndObserveFavoriteUiState(detailViewArg: DetailViewArg) {
        binding.imageFavoriteIcon.setOnClickListener {
            viewModel.favorite.update(detailViewArg)
        }

        viewModel.favorite.state.observe(viewLifecycleOwner) { favoriteIuState ->
            binding.flipperFavorite.displayedChild = when(favoriteIuState) {
                FavoriteUiActionStateLiveData.UiState.Loading -> FLIPPER_FAVORITE_CHILD_POSITION_LOADING
                is FavoriteUiActionStateLiveData.UiState.Icon -> {
                    binding.imageFavoriteIcon.setImageResource(favoriteIuState.icon)
                    FLIPPER_FAVORITE_CHILD_POSITION_IMAGE
                }
                is FavoriteUiActionStateLiveData.UiState.Error -> {
                    showShortToast(favoriteIuState.messageResId)
                    FLIPPER_FAVORITE_CHILD_POSITION_IMAGE
                }
            }
        }
    }

    private fun setSharedElementTransitionOnEnter() {
        TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move).apply {
            sharedElementEnterTransition = this
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_POSITION_LOADING = 0
        private const val FLIPPER_CHILD_POSITION_DETAIL = 1
        private const val FLIPPER_CHILD_POSITION_ERROR = 2
        private const val FLIPPER_CHILD_POSITION_EMPTY = 3

        private const val FLIPPER_FAVORITE_CHILD_POSITION_IMAGE = 0
        private const val FLIPPER_FAVORITE_CHILD_POSITION_LOADING = 1
    }
}