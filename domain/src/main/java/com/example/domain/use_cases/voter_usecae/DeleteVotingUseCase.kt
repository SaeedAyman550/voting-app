package com.example.domain.use_cases.voter_usecae

import com.example.domain.repository.VoterRepo
import com.example.domain.utils.Resourse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import javax.inject.Inject

class DeleteVotingUseCase @Inject constructor(private val repo: VoterRepo) {


    operator fun invoke(id_candidate: String, id_voter: String): Flow<Resourse<ResponseBody>> = flow {

        emit(Resourse.Loading())
        try {
            emit( repo.deleteVoting(id_candidate,id_voter))

        }catch (e:Exception){

            emit( Resourse.Error("${e.message.toString()} handling exception"))
        }

    }

}