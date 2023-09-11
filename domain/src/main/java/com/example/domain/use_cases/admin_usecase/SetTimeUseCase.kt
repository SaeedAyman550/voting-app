package com.example.domain.use_cases.admin_usecase

import com.example.domain.models.Candidate
import com.example.domain.repository.AdminRepo
import com.example.domain.utils.Resourse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import javax.inject.Inject

class SetTimeUseCase @Inject constructor(private val repo:AdminRepo) {


    operator fun invoke(hours: Int): Flow<Resourse<ResponseBody>> = flow {

        emit(Resourse.Loading())
        try {
            emit( repo.setTime(hours))

        }catch (e:Exception){

            emit( Resourse.Error("${e.message.toString()} handling exception"))
        }

    }

}