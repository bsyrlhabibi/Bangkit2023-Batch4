package com.bibi.storyapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bibi.storyapp.data.preferences.UserPreferences
import com.bibi.storyapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepo: UserRepository,
    private val userPref: UserPreferences,
) : ViewModel() {

    fun save(token: String, userName: String) {
        viewModelScope.launch {
            userPref.save(token, userName)
        }
    }

    fun getToken(): LiveData<String> {
        return userPref.getToken().asLiveData()
    }

    fun login(email: String, password: String) = userRepo.login(email, password)

}