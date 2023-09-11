package com.example.domain.use_cases.toke_usecase

import com.example.domain.repository.TokenManagerRepo
import com.example.domain.utils.Resourse
import javax.inject.Inject

class GetVoterTokenUseCase @Inject constructor(private val repo:TokenManagerRepo) {


    suspend  operator fun invoke(): Resourse<String> {


        try {
            return repo.getVoterTokenInSherdPreferences()

        }catch (e:Exception){

            return Resourse.Error("${e.message.toString()} handling exception")

        }



    }

}