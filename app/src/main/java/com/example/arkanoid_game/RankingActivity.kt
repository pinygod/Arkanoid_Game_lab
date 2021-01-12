package com.example.arkanoid_game

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arkanoid_game.databinding.ActivityRankingBinding
import kotlinx.android.synthetic.main.activity_ranking.*


class RankingActivity : AppCompatActivity() {
    private val viewModel by viewModels<RankingViewModel>()
    private lateinit var binding : ActivityRankingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ranking)

    }

    override fun onResume() {
        super.onResume()
        viewModel.recyclerIsReady(::setRecyclerAdapter)
    }

    fun setRecyclerAdapter(adapter: RankingRecyclerAdapter){
        rankingRecycler.setHasFixedSize(true)
        rankingRecycler.layoutManager = LinearLayoutManager(this)
        rankingRecycler.adapter = adapter
    }
}