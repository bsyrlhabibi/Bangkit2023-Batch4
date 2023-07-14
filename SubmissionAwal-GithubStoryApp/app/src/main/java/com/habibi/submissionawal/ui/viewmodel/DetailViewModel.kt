package com.habibi.submissionawal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.habibi.submissionawal.model.Detail
import com.habibi.submissionawal.objek.RetrofitPartners
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    val user = MutableLiveData<Detail>()

    fun setDetail(username: String?){
        if (username != null) {
            RetrofitPartners.apiInstance
                .getDetail(username)
                .enqueue(object : Callback<Detail>{
                    override fun onResponse(call: Call<Detail>, response: Response<Detail>) {
                        if (response.isSuccessful){
                            user.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<Detail>, t: Throwable) {
                        Log.e("Failure", "Error occurred: ${t.message}", t)
                    }

                })
        }
    }
    fun getDetail(): LiveData<Detail>{
        return user
    }
}
