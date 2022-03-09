package com.ezike.tobenna.stockwatcher.ui

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ezike.tobenna.stockwatcher.R
import com.ezike.tobenna.stockwatcher.databinding.LayoutStockViewBinding
import com.ezike.tobenna.stockwatcher.drawable
import com.ezike.tobenna.stockwatcher.inflate
import com.ezike.tobenna.stockwatcher.model.PercentageState
import com.ezike.tobenna.stockwatcher.model.StockModel

class StockDataAdapter : ListAdapter<StockModel, StockDataViewHolder>(StockDataDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockDataViewHolder =
        StockDataViewHolder(
            LayoutStockViewBinding.bind(parent.inflate(R.layout.layout_stock_view))
        )

    override fun onBindViewHolder(holder: StockDataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class StockDataViewHolder(
    private val binding: LayoutStockViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: StockModel) {
        with(binding) {
            name.text = data.name
            price.text = data.price
            bindPercentView(data.percentState)
        }
    }

    private fun LayoutStockViewBinding.bindPercentView(data: PercentageState) {
        val (percent, bg, icon, textColor) = data
        percentView.text = percent
        if (bg != null && icon != null) {
            percentView.setTextColor(ContextCompat.getColor(itemView.context, textColor))
            percentView.background = ContextCompat.getDrawable(itemView.context, bg)
            percentView.drawable(left = icon)
        }
    }
}

private object StockDataDiffCallback : DiffUtil.ItemCallback<StockModel>() {
    override fun areItemsTheSame(
        oldItem: StockModel,
        newItem: StockModel
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: StockModel,
        newItem: StockModel
    ): Boolean = oldItem == newItem
}
