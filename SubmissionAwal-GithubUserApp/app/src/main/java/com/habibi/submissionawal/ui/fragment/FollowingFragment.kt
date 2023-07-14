package com.habibi.submissionawal.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.habibi.submissionawal.R
import com.habibi.submissionawal.adapter.UserAdapter
import com.habibi.submissionawal.databinding.FragmentFollowersFollowingBinding
import com.habibi.submissionawal.ui.DetailActivity
import com.habibi.submissionawal.ui.viewmodel.FollowingViewModel

class FollowingFragment : Fragment(R.layout.fragment_followers_following) {
    private var _binding: FragmentFollowersFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowersFollowingBinding.bind(view)

        val args = arguments
        username = args?.getString(DetailActivity.USERNAME).toString()

        adapter = UserAdapter()
        binding.rvUser.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = this@FollowingFragment.adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowingViewModel::class.java]
        viewModel.setFollowing(username)
        viewModel.getFollowing().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        showLoading(false)
        viewModel.setFollowing(username)
        showLoading(true)
    }

    override fun onPause() {
        super.onPause()
        showLoading(false)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
