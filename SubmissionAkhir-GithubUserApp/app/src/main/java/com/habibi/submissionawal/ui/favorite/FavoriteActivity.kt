package com.habibi.submissionawal.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.habibi.submissionawal.adapter.UserAdapter
import com.habibi.submissionawal.databinding.ActivityFavoriteBinding
import com.habibi.submissionawal.model.Favorite
import com.habibi.submissionawal.model.User
import com.habibi.submissionawal.ui.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        binding.rvUser.adapter = adapter

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.USERNAME, data.login)
                    it.putExtra(DetailActivity.ID, data.id)
                    it.putExtra(DetailActivity.AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })
        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = adapter
        }

        viewModel.getFavorite()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        }
    }

    private fun mapList(favorites: List<Favorite>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (favorite in favorites){
            val userMapped = User(
                favorite.login,
                favorite.id,
                favorite.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}