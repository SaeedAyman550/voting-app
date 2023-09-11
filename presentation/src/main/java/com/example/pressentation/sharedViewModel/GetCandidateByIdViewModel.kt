package com.example.pressentation.sharedViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Candidate
import com.example.domain.use_cases.voter_usecae.GetCandidateByIDUseCase
import com.example.domain.utils.Resourse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetCandidateByIdViewModel @Inject constructor(
    private val get_candidate_by_id: GetCandidateByIDUseCase
    ):ViewModel(){

    private val _get_candidate_by_id_state_flow= MutableStateFlow<Resourse<Candidate>>(Resourse.Loading())
    val get_candidate_by_id_state_flow =_get_candidate_by_id_state_flow.asStateFlow()





    fun getCandidateById(id:String)= get_candidate_by_id(id).onEach {
        when(it){
            is Resourse.Loading->_get_candidate_by_id_state_flow.emit(it)
            is Resourse.Success->_get_candidate_by_id_state_flow.emit(it)
            is Resourse.Error->_get_candidate_by_id_state_flow.emit(it)

        }

    }.launchIn(viewModelScope)


}