package com.example.pressentation.sharedViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Voter
import com.example.domain.use_cases.voter_usecae.*
import com.example.domain.utils.Resourse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetMyVoterViewModel  @Inject constructor(
    private val get_my_voter: GetMyVoterUseCase
): ViewModel(){

    private val _get_my_voter_state_flow= MutableStateFlow<Resourse<Voter>>(Resourse.Loading())
    val get_my_voter_state_flow =_get_my_voter_state_flow.asStateFlow()

    init {
        getMyVoter()
    }

    fun getMyVoter()= get_my_voter().onEach {


        when(it){
            is Resourse.Loading->_get_my_voter_state_flow.emit(it)
            is Resourse.Success-> _get_my_voter_state_flow.emit(it)
            is Resourse.Error->_get_my_voter_state_flow.emit(it)

        }

    }.launchIn(viewModelScope)

}