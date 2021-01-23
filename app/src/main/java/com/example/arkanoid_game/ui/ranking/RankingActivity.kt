package com.example.arkanoid_game.ui.ranking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arkanoid_game.AppHelper
import com.example.arkanoid_game.R
import com.example.arkanoid_game.RankingRecyclerAdapter
import com.example.arkanoid_game.databinding.ActivityRankingBinding


class RankingActivity : AppCompatActivity() {
    private lateinit var viewModel: RankingViewModel
    private lateinit var binding: ActivityRankingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_ranking
        )
        viewModel = ViewModelProviders.of(this,
            AppHelper.viewModelFactory {
                RankingViewModel(this)
            }).get(RankingViewModel::class.java)
        viewModel.playersResults.observe(this, Observer { result ->
            if (result != null && result.isNotEmpty()) {
                (binding.rankingRecycler.adapter as RankingRecyclerAdapter).updateList(result)
            } else
                return@Observer
        })
        setRecyclerAdapter(
            RankingRecyclerAdapter(
                viewModel.playersResults.value!!
            )
        )
    }

    private fun setRecyclerAdapter(adapter: RankingRecyclerAdapter) {
        binding.rankingRecycler.setHasFixedSize(true)
        binding.rankingRecycler.layoutManager = LinearLayoutManager(this)
        binding.rankingRecycler.adapter = adapter
    }

}