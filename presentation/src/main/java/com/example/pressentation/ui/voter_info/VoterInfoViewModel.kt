package com.example.pressentation.ui.voter_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Candidate
import com.example.domain.models.Time
import com.example.domain.models.Voter
import com.example.domain.use_cases.voter_usecae.DeleteVotingUseCase
import com.example.domain.use_cases.voter_usecae.GetCandidateByIDUseCase
import com.example.domain.use_cases.voter_usecae.GetMyVoterUseCase
import com.example.domain.use_cases.voter_usecae.GetTimeUseCase
import com.example.domain.utils.Resourse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class VoterInfoViewModel @Inject constructor(

    private val delete_voting:DeleteVotingUseCase,


):ViewModel() {


    private val _delete_voting_state_flow= MutableStateFlow<Resourse<ResponseBody>>(Resourse.Loading())
    val delete_voting_state_flow =_delete_voting_state_flow.asStateFlow()



    fun deleteVoting(id_candidate: String, id_voter: String)=delete_voting(id_candidate,id_voter).onEach {


        when(it) {
            is Resourse.Loading -> _delete_voting_state_flow.emit(it)
            is Resourse.Success -> _delete_voting_state_flow.emit(it)
            is Resourse.Error -> _delete_voting_state_flow.emit(it)
        }
        }.launchIn(viewModelScope)





}