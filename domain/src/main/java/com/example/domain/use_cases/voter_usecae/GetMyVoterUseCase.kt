package com.example.domain.use_cases.voter_usecae

import com.example.domain.models.Voter
import com.example.domain.repository.VoterRepo
import com.example.domain.utils.Resourse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import javax.inject.Inject

class GetMyVoterUseCase @Inject constructor(private val repo: VoterRepo) {



    operator fun invoke(): Flow<Resourse<com.example.domain.models.Voter>> = flow {

        emit(Resourse.Loading())
        try {
            emit( repo.getMyVoter())

        }catch (e:Exception){

            emit( Resourse.Error("${e.message.toString()} handling exception"))
        }

    }

}