package com.ebenezer.gana.aisattendance.ui.admin.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ebenezer.gana.aisattendance.R
import com.ebenezer.gana.aisattendance.databinding.FragmentAdminAttendanceDetailsBinding
import com.ebenezer.gana.aisattendance.databinding.FragmentAdminDashboardBinding
import com.ebenezer.gana.aisattendance.ui.attendanceList.AttendanceListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminAttendanceDetailsFragment : Fragment() {

    private var _binding: FragmentAdminAttendanceDetailsBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: AdminAttendanceDetailsFragmentArgs by navArgs()
    private val viewModel:AdminAttendanceDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminAttendanceDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.id
        viewModel.getAttendanceList(id)

        binding.rvAttendance.layoutManager = LinearLayoutManager(this.context)

        //Let's reuse AttendanceListAdapter
        val adapter = AttendanceListAdapter{}
        binding.rvAttendance.adapter = adapter
        viewModel.attendanceList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}