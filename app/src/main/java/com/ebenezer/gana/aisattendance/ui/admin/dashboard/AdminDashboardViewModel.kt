package com.ebenezer.gana.aisattendance.ui.admin.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    /**
     * A function that will be called from AdminDashboardFragment to get the list
     * of the overall days attendance
     */
    fun getAdminListOfAttendance(){
        viewModelScope.launch {
            repository.getListOfAttendances {
                _todayAttendance.value = it
            }
        }
    }

    /**
     * A public function that will be called from AdminDashboardFragment to create an new
     * list of attendance ready to accepts data from the users
     * @param day Today's date
     */
    fun createAttendance(day:Day) {
        createNewAttendanceSheet(day)
    }

    /**
     * A private function to do the actual creation of the attendance list. The data is delegated to AdminRepository
     */
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