package com.ebenezer.gana.aisattendance.ui.attendanceList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ebenezer.gana.aisattendance.data.models.Attendance
import com.ebenezer.gana.aisattendance.databinding.StaffListItemBinding

class AttendanceListAdapter(private val onItemClicked: (Attendance) -> Unit) :
    ListAdapter<Attendance, AttendanceListAdapter.AttendanceViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        return AttendanceViewHolder(
            StaffListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
    }

    class AttendanceViewHolder(private var binding: StaffListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(attendance: Attendance) {
            binding.apply {
                staffName.text = attendance.name
                attendanceTime.text = attendance.time
            }
        }

    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Attendance>() {
            override fun areItemsTheSame(oldItem: Attendance, newItem: Attendance): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Attendance, newItem: Attendance): Boolean {
                return oldItem.id == newItem.id && oldItem.time == newItem.time
            }
        }
    }

}