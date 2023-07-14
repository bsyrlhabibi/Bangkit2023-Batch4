package com.habibi.submissionawal.ui.detail.followers


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.habibi.submissionawal.R
import com.habibi.submissionawal.adapter.UserAdapter
import com.habibi.submissionawal.databinding.FragmentFollowersFollowingBinding
import com.habibi.submissionawal.ui.detail.DetailActivity

class FollowersFragment : Fragment(R.layout.fragment_followers_following) {

    private var _binding: FragmentFollowersFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailActivity.USERNAME).toString()
        _binding = FragmentFollowersFollowingBinding.bind(view)

        adapter = UserAdapter()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowersViewModel::class.java]
        viewModel.setFollowers(username)
        viewModel.getFollowers().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showLoading(false)
        viewModel.setFollowers(username)
        showLoading(true)
    }

    override fun onPause() {
        super.onPause()
        showLoading(false)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}