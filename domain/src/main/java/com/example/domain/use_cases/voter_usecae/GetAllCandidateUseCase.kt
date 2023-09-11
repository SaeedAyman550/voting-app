package com.example.domain.use_cases.voter_usecae

import com.example.domain.models.Candidate
import com.example.domain.repository.VoterRepo
import com.example.domain.utils.Resourse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllCandidateUseCase @Inject constructor(private val repo: VoterRepo) {


     operator fun invoke(): Flow<Resourse<List<com.example.domain.models.Candidate>>> = flow {

        emit(Resourse.Loading())
        try {
            emit( repo.getAllCandidate())

        }catch (e:Exception){

            emit( Resourse.Error("${e.message.toString()} handling exception"))
        }

    }



}