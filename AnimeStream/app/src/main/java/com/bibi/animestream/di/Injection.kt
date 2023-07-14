package com.bibi.animestream.di

import com.bibi.animestream.data.AnimeRepository

object Injection {
    fun provideRepository(): AnimeRepository {
        return AnimeRepository.getInstance()
    }
}