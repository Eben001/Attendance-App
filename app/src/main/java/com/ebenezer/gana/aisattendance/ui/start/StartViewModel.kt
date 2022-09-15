package com.ebenezer.gana.aisattendance.ui.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ebenezer.gana.aisattendance.data.models.Day
import com.ebenezer.gana.aisattendance.data.repository.StartScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(private val repository: StartScreenRepository) :
    ViewModel() {

    private val _availableAttendance = MutableLiveData<Day>()
    val availableAttendance: LiveData<Day> = _availableAttendance

    /**
     * A function to get the latest attendance created by the admin
     */
    fun getAvailableAttendance() {
        repository.getAvailableAttendance {
            _availableAttendance.value = it
        }
    }
}