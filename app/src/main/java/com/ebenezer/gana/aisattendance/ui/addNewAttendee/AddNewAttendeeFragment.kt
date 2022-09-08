package com.ebenezer.gana.aisattendance.ui.addNewAttendee

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ebenezer.gana.aisattendance.databinding.FragmentAddNewAttendeeBinding
import com.ebenezer.gana.aisattendance.ui.baseFragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddNewAttendeeFragment : BaseFragment() {
    private var _binding: FragmentAddNewAttendeeBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs:AddNewAttendeeFragmentArgs by navArgs()

    private val viewModel: AddNewAttendeeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNewAttendeeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentAttendanceId = navigationArgs.attendanceId

        observeViewModels()
        setOnClickListeners(currentAttendanceId)
    }

    private fun setOnClickListeners(id:String) {
        binding.submitAction.setOnClickListener {
            if (isValidDetails()) {
                submitAttendance(id)
                clearTextEntries()
                hideKeyboard()
            }
        }
    }

    private fun observeViewModels() {
        viewModel.result.observe(viewLifecycleOwner) {
            if (viewModel.isPostSuccess.value == true) {
                showSnackBar(it.asString(requireContext()), isError = false)
            } else {
                showSnackBar(it.asString(requireContext()), isError = true)
            }
        }

    }

    private fun submitAttendance(currentAttendanceId:String) {
        val dateFormat =
            SimpleDateFormat("h:mm a", Locale.getDefault()) // e.g  9:43AM
        val currentDateAndTime: String = dateFormat.format(Calendar.getInstance().timeInMillis)

        viewModel.submitAttendance(
            id = currentAttendanceId,
            userId = "",
            name = binding.attendeeName.text.toString(),
            time = currentDateAndTime
        )
    }

    private fun clearTextEntries() {
        binding.attendeeName.text?.clear()
    }

    private fun isValidDetails(): Boolean {
        return when {
            binding.attendeeName
                .text.toString().trim().isEmpty() -> {
                Toast.makeText(requireContext(), "Please Enter your name", Toast.LENGTH_SHORT)
                    .show()
                false
            }

            else -> true
        }

    }

    private fun hideKeyboard() {
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }


    override fun onDestroy() {
        super.onDestroy()
        hideKeyboard()
        _binding = null
    }


}