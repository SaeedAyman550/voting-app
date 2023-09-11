package com.example.pressentation.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Agenda
import com.example.domain.use_cases.admin_usecase.CreateCandidateUseCase
import com.example.domain.use_cases.admin_usecase.CreateVoterUseCase
import com.example.domain.use_cases.admin_usecase.LoginAdminUseCase
import com.example.domain.use_cases.admin_usecase.SetTimeUseCase
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
class AdminViewModel @Inject constructor(
    private val create_candidate:CreateCandidateUseCase,
    private val create_voter:CreateVoterUseCase,
    private val login_admin:LoginAdminUseCase,
    private val set_time: SetTimeUseCase
    ) : ViewModel() {




    private val _create_candidate_state_flow= MutableStateFlow<Resourse<ResponseBody>>(Resourse.Error("initial state"))
    val create_candidate_state_flow =_create_candidate_state_flow.asStateFlow()

   private  val _create_voter_state_flow= MutableStateFlow<Resourse<ResponseBody>>(Resourse.Error("initial state"))
    val create_voter_state_flow =_create_voter_state_flow.asStateFlow()

    private val _login_admin_state_flow= MutableStateFlow<Resourse<String>>(Resourse.Error("initial state"))
    val login_admin_state_flow  = _login_admin_state_flow.asStateFlow()


    private val _set_time_state_flow = MutableStateFlow<Resourse<ResponseBody>>(Resourse.Loading())
    val set_time_state_flow =_set_time_state_flow.asStateFlow()

    fun createCandidate(name: String, code: String, date: String, political_party: String, agenda_list: List<Agenda>, realPath:String)= viewModelScope.launch {

        _create_candidate_state_flow.emit(Resourse.Loading())

        try {
            if (realPath.isEmpty())
                _create_voter_state_flow.emit(Resourse.Error("no size enough"))
            else {
                val result=  create_candidate(name,code,date,political_party,agenda_list,realPath)
                _create_candidate_state_flow.emit(result)
            }
        }
        catch (e:Exception){
            _create_candidate_state_flow.emit(Resourse.Error(e.message.toString()))
        }

    }


    fun createVoter(id: Long, name: String, birth_day: String, city: String, realPath:String)=viewModelScope.launch {
        _create_voter_state_flow.emit(Resourse.Loading())
        try {
            if (realPath.isEmpty())
                _create_voter_state_flow.emit(Resourse.Error("no size enough"))
            else {
                val result = create_voter(id, name, birth_day, city, realPath)
                _create_voter_state_flow.emit(result)
            }


        } catch (e: Exception) {
            _create_voter_state_flow.emit(Resourse.Error(e.message.toString()))
        }


    }


    fun loginAdmin(email: String, password: String)=login_admin(email,password).onEach {

        when(it){
            is Resourse.Loading->_login_admin_state_flow.emit(it)
            is Resourse.Success->_login_admin_state_flow.emit(it)
            is Resourse.Error->_login_admin_state_flow.emit(it)

        }
    }.launchIn(viewModelScope)

    fun setTime(hours:Int)= set_time(hours).onEach {

        when(it){
            is Resourse.Loading->_set_time_state_flow.emit(it)
            is Resourse.Success->_set_time_state_flow.emit(it)
            is Resourse.Error->_set_time_state_flow.emit(it)

        }

    }.launchIn(viewModelScope)




}