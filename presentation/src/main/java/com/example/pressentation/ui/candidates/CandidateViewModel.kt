package com.example.pressentation.ui.candidates

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Agenda
import com.example.domain.models.Candidate
import com.example.domain.models.Time
import com.example.domain.models.Voter
import com.example.domain.use_cases.voter_usecae.*
import com.example.domain.utils.Resourse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject
@HiltViewModel
class CandidateViewModel @Inject constructor(

    private val set_voting:SetVotingUseCase,

):ViewModel() {


    private val _set_voting_state_flow= MutableStateFlow<Resourse<ResponseBody>>(Resourse.Loading())
    val set_voting_state_flow =_set_voting_state_flow.asStateFlow()


    fun setVoting(id_candidate: String, id_voter: String)= set_voting(id_candidate,id_voter).onEach {

        when(it){
            is Resourse.Loading->_set_voting_state_flow.emit(it)
            is Resourse.Success->_set_voting_state_flow.emit(it)
            is Resourse.Error->_set_voting_state_flow.emit(it)

        }

    }.launchIn(viewModelScope)







}