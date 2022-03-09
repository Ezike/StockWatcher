package com.ezike.tobenna.stockwatcher.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ezike.tobenna.stockwatcher.databinding.ActivityMainBinding
import com.ezike.tobenna.stockwatcher.drawable
import com.ezike.tobenna.stockwatcher.model.DataUpdateState
import com.ezike.tobenna.stockwatcher.string
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val stockDataAdapter = StockDataAdapter()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.stocksRv.adapter = stockDataAdapter

        lifecycleScope.launch {
            viewModel.stocks.flowWithLifecycle(lifecycle).collect { state ->
                stockDataAdapter.submitList(state.data)
            }
        }

        lifecycleScope.launch {
            viewModel.liveUpdate.flowWithLifecycle(lifecycle).collect { state ->
                renderUpdateState(state, binding)
            }
        }
    }

    private fun renderUpdateState(
        state: DataUpdateState,
        binding: ActivityMainBinding
    ) {
        binding.errorView.isVisible = state.showError
        with(binding.lastUpdated) {
            string = state.title
            drawable(left = state.icon)
            isVisible = state.showLiveBanner
        }
    }
}
