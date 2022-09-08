package com.ebenezer.gana.aisattendance.ui.admin.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ebenezer.gana.aisattendance.data.models.Attendance
import com.ebenezer.gana.aisattendance.data.repository.StaffListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminAttendanceDetailsViewModel @Inject constructor(private val repository: StaffListRepository) :
    ViewModel() {

    private val _attendanceList = MutableLiveData<List<Attendance>>()
    val attendanceList: LiveData<List<Attendance>> = _attendanceList


    fun getAttendanceList(id: String) {
        repository.getAttendanceList(id) {
            _attendanceList.value = it
        }
    }


}