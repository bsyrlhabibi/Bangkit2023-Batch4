package com.habibi.submissionawal.ui.activity

import android.util.Log
import androidx.lifecycle.*
import com.habibi.submissionawal.model.User
import com.habibi.submissionawal.model.UserResponds
import com.habibi.submissionawal.objek.RetrofitPartners
import com.habibi.submissionawal.setting.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val preferences: SettingPreferences) : ViewModel() {


    private val listUsers = MutableLiveData<ArrayList<User>>()

    fun getTheme() = preferences.getThemeSetting().asLiveData(

    )

    fun setSearchUsers(query: String) {
        RetrofitPartners.apiInstance
            .getSearchUsers(query, com.habibi.submissionawal.BuildConfig.GITHUB_TOKEN)
            .enqueue(object : Callback<UserResponds> {
                override fun onResponse(
                    call: Call<UserResponds>,
                    response: Response<UserResponds>,
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponds>, t: Throwable) {
                    Log.e("Failure", "Error occurred: ${t.message}", t)
                }
            })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }

    class Factory(private val preferences: SettingPreferences) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(preferences) as T
    }
}
