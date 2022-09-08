package com.ebenezer.gana.aisattendance.ui.admin.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ebenezer.gana.aisattendance.data.models.Day
import com.ebenezer.gana.aisattendance.databinding.AdminListItemAttendanceBinding
import java.text.SimpleDateFormat
import java.util.*

class AdminDashboardListAdapter(private val onItemClicked: (Day) -> Unit) :
    ListAdapter<Day, AdminDashboardListAdapter.AdminViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        return AdminViewHolder(
            AdminListItemAttendanceBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
    }

    class AdminViewHolder(private var binding: AdminListItemAttendanceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(day: Day) {
            binding.apply {
                date.text = day.day
            }
        }
    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Day>() {
            override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
                return oldItem.id == newItem.id && oldItem.timeStamp == newItem.timeStamp
            }
        }
    }


}