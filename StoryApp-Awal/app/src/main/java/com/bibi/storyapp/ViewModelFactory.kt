package com.bibi.storyapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bibi.storyapp.data.preferences.UserPreferences
import com.bibi.storyapp.data.repository.UserRepository
import com.bibi.storyapp.ui.login.LoginViewModel
import com.bibi.storyapp.ui.register.RegisterViewModel

class ViewModelFactory(
    private val userRepo: UserRepository,
    private val sessionPref: UserPreferences
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userRepo) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepo, sessionPref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    UserInjection.provideRepository(),
                    UserInjection.providePreferences(context)
                )
            }.also { instance = it }
    }

}
