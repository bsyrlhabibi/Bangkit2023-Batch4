package com.bibi.storyapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bibi.storyapp.viewmodel.LoginPreferences
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: LoginPreferences) : ViewModel() {
    fun isLoggedIn(): LiveData<Boolean> = pref.isLoggedIn().asLiveData()

    fun setLoggedIn(isLoggedIn: Boolean) {
        viewModelScope.launch {
            pref.setLoggedIn(isLoggedIn)
        }
    }

    fun setToken(token: String) {
        viewModelScope.launch {
            pref.setToken(token)
        }
    }

    fun getToken(): LiveData<String> = pref.getToken().asLiveData()
}