package com.example.pressentation.sharedViewModel

import androidx.lifecycle.ViewModel
import com.example.domain.use_cases.toke_usecase.GetAdminTokenUseCase
import com.example.domain.use_cases.toke_usecase.GetVoterTokenUseCase
import com.example.domain.use_cases.toke_usecase.SetAdminTokenUseCase
import com.example.domain.use_cases.toke_usecase.SetVoterTokenUseCase
import com.example.domain.utils.Resourse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TokenManagerViewModel @Inject constructor(
    private val set_admin_token: SetAdminTokenUseCase,
    private val get_admin_token: GetAdminTokenUseCase,
    private val set_voter_token: SetVoterTokenUseCase,
    private val get_voter_token: GetVoterTokenUseCase

): ViewModel() {

   suspend fun setAdminToken(token:String): Resourse<String> {
       try {

           return set_admin_token(token)

       } catch (e: Exception) {

           return Resourse.Error(e.message.toString())

       }

    }

   suspend fun getAdminToken():Resourse<String>{
       try {
           return get_admin_token()

       }catch (e:Exception){

           return Resourse.Error(e.message.toString())

       }

    }

    suspend fun setVoterToken(token:String): Resourse<String> {
        try {

            return set_voter_token(token)

        } catch (e: Exception) {

            return Resourse.Error(e.message.toString())

        }

    }

    suspend fun getVoterToken():Resourse<String>{
        try {
            return get_voter_token()

        }catch (e:Exception){

            return Resourse.Error(e.message.toString())

        }

    }

}