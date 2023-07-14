package com.bibi.animestream.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibi.animestream.data.AnimeRepository
import com.bibi.animestream.model.Anime
import com.bibi.animestream.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: AnimeRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Anime>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Anime>>>
        get() = _uiState

    fun getFavoritePlayer() = viewModelScope.launch {
        repository.getFavoriteAnimeList()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateAnime(id: Int, newState: Boolean) {
        repository.updateAnimeFavoriteStatus(id, newState)
        getFavoritePlayer()
    }
}