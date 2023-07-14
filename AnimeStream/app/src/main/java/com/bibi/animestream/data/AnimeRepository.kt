package com.bibi.animestream.data

import com.bibi.animestream.model.AnimeData
import com.bibi.animestream.model.Anime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class AnimeRepository {
    private val dummyAnime = mutableListOf<Anime>()

    init {
        if (dummyAnime.isEmpty()) {
            AnimeData.dummyAnime.forEach {
                dummyAnime.add(it)
            }
        }
    }

    fun  getAnimeById(playerId: Int): Anime {
        return dummyAnime.first {
            it.id == playerId
        }
    }

    fun getFavoriteAnimeList(): Flow<List<Anime>> {
        return flowOf(dummyAnime.filter { it.isFavorite })
    }

    fun searchAnime(query: String) = flow {
        val data = dummyAnime.filter {
            it.title.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun updateAnimeFavoriteStatus(playerId: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyAnime.indexOfFirst { it.id == playerId }
        val result = if (index >= 0) {
            val player = dummyAnime[index]
            dummyAnime[index] = player.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: AnimeRepository? = null

        fun getInstance(): AnimeRepository =
            instance ?: synchronized(this) {
                AnimeRepository().apply {
                    instance = this
                }
            }
    }
}