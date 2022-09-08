package com.ebenezer.gana.aisattendance.ui.addNewAttendee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ebenezer.gana.aisattendance.data.models.Attendance
import com.ebenezer.gana.aisattendance.data.repository.StaffListRepository
import com.ebenezer.gana.aisattendance.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddNewAttendeeViewModel @Inject constructor(private val repository: StaffListRepository) :
    ViewModel() {
    private var _result = MutableLiveData<UiText>()
    val result: LiveData<UiText> = _result

    private var _isPostSuccess = MutableLiveData<Boolean>()
    val isPostSuccess:LiveData<Boolean> = _isPostSuccess

    fun submitAttendance(
        id: String,
        userId: String,
        name: String,
        time: String,
    ) {
        val newData = Attendance(id, userId, name, time)

        submitNewAttendance(newData)


    }

    private fun submitNewAttendance(newData: Attendance) {
        repository.addNewAttendee(newData,
            onSuccess = {
                _isPostSuccess.value = true
                _result.value = it
            },
            onFailure = {
                _isPostSuccess.value = false
                _result.value = it
            })

    }
}