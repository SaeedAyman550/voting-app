package com.example.pressentation.sharedViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Candidate
import com.example.domain.use_cases.voter_usecae.GetAllCandidateUseCase
import com.example.domain.utils.Resourse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetAllCandidatesViewModel @Inject constructor(
    private val get_all_candidate: GetAllCandidateUseCase
    ):ViewModel() {


    private val _get_all_candidate_state_flow= MutableStateFlow<Resourse<List<Candidate>>>(Resourse.Loading())
    val get_all_candidate_state_flow =_get_all_candidate_state_flow.asStateFlow()


    init {
        getAllCandidates()
    }

    fun getAllCandidates()= get_all_candidate().onEach {

        when(it){
            is Resourse.Loading->_get_all_candidate_state_flow.emit(it)
            is Resourse.Success-> _get_all_candidate_state_flow.emit(Resourse.Success(it.data!!))

            is Resourse.Error->_get_all_candidate_state_flow.emit(it)

        }

    }.launchIn(viewModelScope)


}