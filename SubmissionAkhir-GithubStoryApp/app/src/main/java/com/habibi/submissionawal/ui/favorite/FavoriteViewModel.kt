package com.habibi.submissionawal.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.habibi.submissionawal.dao.FavoriteDao
import com.habibi.submissionawal.database.UserDatabase
import com.habibi.submissionawal.model.Favorite

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDao: FavoriteDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteDao()
    }
    fun getFavorite(): LiveData<List<Favorite>>? {
        return userDao?.getFavorite()
    }
}