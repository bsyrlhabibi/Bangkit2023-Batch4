package com.bibi.animestream.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailAnime : Screen("home/{animeId}") {
        fun createRoute(animeId: Int) = "home/$animeId"
    }
}