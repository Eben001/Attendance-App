package com.ebenezer.gana.aisattendance.ui.attendanceList

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ebenezer.gana.aisattendance.R
import com.ebenezer.gana.aisattendance.data.models.Day
import com.ebenezer.gana.aisattendance.databinding.FragmentAttendanceListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttendanceListFragment : Fragment() {

    private var _binding: FragmentAttendanceListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AttendanceListViewModel by viewModels()

    private val navigationArgs: AttendanceListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAttendanceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val day = navigationArgs.day
        bind(day)
        viewModel.getAttendanceList(day.id!!)

        binding.rvAttendance.layoutManager = LinearLayoutManager(this.context)
        val adapter = AttendanceListAdapter { }

        binding.rvAttendance.adapter = adapter
        viewModel.attendanceList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.floatingActionButton.setOnClickListener {
            val action =
                AttendanceListFragmentDirections.actionAttendanceListFragmentToAddNewAttendeeFragment(
                    day.id!!
                )
            findNavController().navigate(action)
        }
    }

    fun bind(day: Day){
        binding.day.text = day.day
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}