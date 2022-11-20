package com.ezike.tobenna.stockwatcher.ui

import android.view.View
import androidx.core.view.isVisible
import com.ezike.tobenna.stockwatcher.Diffuser
import com.ezike.tobenna.stockwatcher.Renderer
import com.ezike.tobenna.stockwatcher.databinding.ActivityMainBinding
import com.ezike.tobenna.stockwatcher.drawable
import com.ezike.tobenna.stockwatcher.model.ConnectionState
import com.ezike.tobenna.stockwatcher.model.ViewState
import com.ezike.tobenna.stockwatcher.string
import javax.inject.Inject

internal interface RootUi {
    val view: View
    fun render(state: ViewState)
}

internal class RootUiImpl @Inject constructor(
    binding: ActivityMainBinding,
) : RootUi {

    override val view: View = binding.root

    private val stockDataAdapter = StockDataAdapter()
        .apply(binding.stocksRv::setAdapter)

    private val renderer = Renderer(
        Diffuser(ViewState::data, stockDataAdapter::submitList),
        Diffuser(
            ViewState::connectionState,
            Renderer(
                Diffuser(ConnectionState::isError, binding.errorView::isVisible::set),
                Diffuser(ConnectionState::title, binding.lastUpdated::string::set),
                Diffuser(ConnectionState::icon, binding.lastUpdated::drawable),
            )
        )
    )

    override fun render(state: ViewState) {
        renderer(state)
    }
}

