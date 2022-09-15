package com.ebenezer.gana.aisattendance.ui.attendanceList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebenezer.gana.aisattendance.data.models.Attendance
import com.ebenezer.gana.aisattendance.data.repository.StaffListRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendanceListViewModel @Inject constructor(private val repository: StaffListRepository) : ViewModel() {

    private val _attendanceList = MutableLiveData<List<Attendance>>()
    val attendanceList:LiveData<List<Attendance>> = _attendanceList

    /**
     * A function to return the list of a specific day's attendance
     * @param id Id of the particular day
     */
    fun getAttendanceList(id:String){
        viewModelScope.launch {
            repository.getAttendanceList(id) {
                _attendanceList.value = it
            }
        }
    }
}