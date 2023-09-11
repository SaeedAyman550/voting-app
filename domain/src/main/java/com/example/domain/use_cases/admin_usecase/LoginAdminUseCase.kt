package com.example.domain.use_cases.admin_usecase

import com.example.domain.repository.AdminRepo
import com.example.domain.utils.Resourse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import javax.inject.Inject

class LoginAdminUseCase @Inject constructor(private val repo: AdminRepo)  {


    operator fun invoke(email: String, password: String): Flow<Resourse<String>> = flow {

        emit(Resourse.Loading())
        try {
            emit( repo.loginAdmin(email,password)!!)

        }catch (e:Exception){

            emit(Resourse.Error("${e.message.toString()} handling exception"))
        }

    }


}