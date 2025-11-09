package com.uno.veterinaria.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uno.veterinaria.databinding.HourItemLayoutBinding

class HoursAdapter(private val onHourClick: (String) -> Unit) : ListAdapter<String, HoursAdapter.HourViewHolder>(DiffCallback) {

    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val binding = HourItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        val hour = getItem(position)
        holder.bind(hour, position)
    }

    inner class HourViewHolder(private val binding: HourItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hour: String, position: Int) {
            binding.hourTextView.text = hour
            binding.root.isSelected = selectedPosition == position

            binding.root.setOnClickListener {
                if (selectedPosition != position) {
                    val oldPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(oldPosition)
                    notifyItemChanged(selectedPosition)
                    onHourClick(hour)
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}