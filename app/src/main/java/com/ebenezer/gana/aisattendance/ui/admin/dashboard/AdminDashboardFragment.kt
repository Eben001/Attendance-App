package com.ebenezer.gana.aisattendance.ui.admin.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ebenezer.gana.aisattendance.R
import com.ebenezer.gana.aisattendance.data.models.Day
import com.ebenezer.gana.aisattendance.databinding.FragmentAdminDashboardBinding
import com.ebenezer.gana.aisattendance.databinding.FragmentStartBinding
import com.ebenezer.gana.aisattendance.ui.attendanceList.AttendanceListAdapter
import com.ebenezer.gana.aisattendance.ui.baseFragment.BaseFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AdminDashboardFragment : BaseFragment() {


    private var _binding: FragmentAdminDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel:AdminDashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModels()
        viewModel.getTodayAttendanceList()
        setOnClickListeners()

        binding.rvAttendance.layoutManager = LinearLayoutManager(this.context)
        val adapter = AdminDashboardListAdapter{day ->
            val action = AdminDashboardFragmentDirections.actionAdminDashboardFragmentToAdminAttendanceDetailsFragment(
                day.id!!
            )
            findNavController().navigate(action)

        }

        binding.rvAttendance.adapter = adapter
        viewModel.todayAttendance.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.swipeRefresh.isRefreshing = false

        }

    }

    private fun setOnClickListeners() {
        binding.floatingActionButton.setOnClickListener {
            showConfirmDialog()
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getTodayAttendanceList()
            binding.swipeRefresh.isRefreshing = true
        }
    }

    private fun observeViewModels() {
        viewModel.result.observe(viewLifecycleOwner){
            if (viewModel.isPostSuccess.value == true) {
                showSnackBar(it.asString(requireContext()), isError = false)
            } else {
                showSnackBar(it.asString(requireContext()), isError = true)
            }
        }


    }

    private fun showConfirmDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.add_attendance))
            .setMessage(resources.getString(R.string.add_dialog_message))
            .setNeutralButton(resources.getString(R.string.cancel_dialog_message)) { dialog, _ ->
                dialog.cancel()
            }
            .setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
               createAttendance()
            }
            .show()
    }

    private fun createAttendance() {
        val dateFormat =
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) // e.g  1 Oct 2022
        val currentDate: String = dateFormat.format(Calendar.getInstance().timeInMillis)


        val day = Day(id = "",
        currentDate, "",)

        viewModel.createAttendance(day)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}