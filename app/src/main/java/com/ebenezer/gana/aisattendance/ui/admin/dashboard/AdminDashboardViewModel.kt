package com.ebenezer.gana.aisattendance.ui.admin.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebenezer.gana.aisattendance.data.models.AdminAttendance
import com.ebenezer.gana.aisattendance.data.models.Attendance
import com.ebenezer.gana.aisattendance.data.models.Day
import com.ebenezer.gana.aisattendance.data.repository.AdminRepository
import com.ebenezer.gana.aisattendance.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminDashboardViewModel @Inject constructor(private val repository: AdminRepository) :
    ViewModel() {

    private var _result = MutableLiveData<UiText>()
    val result: LiveData<UiText> = _result

    private var _isPostSuccess = MutableLiveData<Boolean>()
    val isPostSuccess: LiveData<Boolean> = _isPostSuccess

    private var _todayAttendance = MutableLiveData<List<Day>>()
    val todayAttendance:LiveData<List<Day>> = _todayAttendance


    fun getTodayAttendanceList(){
        viewModelScope.launch {
            repository.getTodayAttendanceList {
                _todayAttendance.value = it
            }
        }
    }

    fun createAttendance(day:Day) {
        createNewAttendanceSheet(day)
    }

    private fun createNewAttendanceSheet(newData: Day) {
        repository.createAttendance(newData,
            onSuccess = {
                _isPostSuccess.value = true
                _result.value = it
            },
            onFailure = {
                _result.value = it
                _isPostSuccess.value = false
            })
    }
}