package com.example.pressentation.ui.login_voter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.LoginVoterResponse
import com.example.domain.use_cases.voter_usecae.LoginVoterUseCase
import com.example.domain.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class VoterAuthViewModel @Inject constructor(
    private val login_voter:LoginVoterUseCase
):ViewModel() {




    private val _login_voter_state_flow= MutableStateFlow<Resourse<LoginVoterResponse>>(Resourse.Error("init state"))
    val login_voter_state_flow =_login_voter_state_flow.asStateFlow()


    fun loginVoter(personal_id:Long, realPath:String)=viewModelScope.launch {
        _login_voter_state_flow.emit(Resourse.Loading())
        try {
                if (realPath=="")
                    _login_voter_state_flow.emit(Resourse.Error("error"))

                else {
                    val result = login_voter(personal_id, realPath)
                    _login_voter_state_flow.emit(result)
                }

        }catch (e:Exception){
            _login_voter_state_flow.emit(Resourse.Error(e.message.toString()))
        }


    }


}