package com.example.pressentation.sharedViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Time
import com.example.domain.use_cases.voter_usecae.GetTimeUseCase
import com.example.domain.utils.Resourse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetTimeViewModel @Inject constructor(
    private val get_time: GetTimeUseCase
    ):ViewModel() {


    private val _get_time_state_flow = MutableStateFlow<Resourse<Time>>(Resourse.Loading())
    val get_time_state_flow =_get_time_state_flow.asStateFlow()


    init {
        getTime()
    }

    fun getTime()= get_time().onEach {

        when(it){
            is Resourse.Loading->_get_time_state_flow.emit(it)
            is Resourse.Success->_get_time_state_flow.emit(it)
            is Resourse.Error->_get_time_state_flow.emit(it)

        }

    }.launchIn(viewModelScope)




}