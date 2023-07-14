package com.habibi.submissionawal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.habibi.submissionawal.model.User
import com.habibi.submissionawal.model.UserResponds
import com.habibi.submissionawal.objek.RetrofitPartners
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainView : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setSearchUsers(query: String){
        RetrofitPartners.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponds> {
                override fun onResponse(
                    call: Call<UserResponds>,
                    response: Response<UserResponds>,
                ) {
                    if (response.isSuccessful){
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

}