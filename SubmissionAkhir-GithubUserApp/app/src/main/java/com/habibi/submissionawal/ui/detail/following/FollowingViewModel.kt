package com.habibi.submissionawal.ui.detail.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.habibi.submissionawal.BuildConfig
import com.habibi.submissionawal.model.User
import com.habibi.submissionawal.objek.RetrofitPartners
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    val nameFollowing = MutableLiveData<ArrayList<User>>()

    fun setFollowing(username: String) {
        RetrofitPartners.apiInstance
            .getFollowing(username, BuildConfig.GITHUB_TOKEN)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>,
                ) {
                    if (response.isSuccessful) {
                        nameFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e("Failure", "Error occurred: ${t.message}", t)
                }

            })
    }

    fun getFollowing(): LiveData<ArrayList<User>> {
        return nameFollowing
    }
}