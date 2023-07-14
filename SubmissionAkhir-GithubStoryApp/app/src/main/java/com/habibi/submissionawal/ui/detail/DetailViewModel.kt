package com.habibi.submissionawal.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.habibi.submissionawal.BuildConfig
import com.habibi.submissionawal.dao.FavoriteDao
import com.habibi.submissionawal.database.UserDatabase
import com.habibi.submissionawal.model.Detail
import com.habibi.submissionawal.model.Favorite
import com.habibi.submissionawal.objek.RetrofitPartners
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<Detail>()

    private var userDao: FavoriteDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteDao()
    }


    fun setDetail(username: String?) {
        if (username != null) {
            RetrofitPartners.apiInstance
                .getDetail(username, BuildConfig.GITHUB_TOKEN)
                .enqueue(object : Callback<Detail> {
                    override fun onResponse(call: Call<Detail>, response: Response<Detail>) {
                        if (response.isSuccessful) {
                            user.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<Detail>, t: Throwable) {
                        Log.e("Failure", "Error occurred: ${t.message}", t)
                    }

                })
        }
    }

    fun getDetail(): LiveData<Detail> {
        return user
    }

    fun addFavorite(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = Favorite(
                login = username,
                id = id,
                avatar_url = avatarUrl
            )
            userDao?.addFavorite(user)
        }
    }


    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun uncheckUser(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.uncheckUser(id)
        }
    }
}
