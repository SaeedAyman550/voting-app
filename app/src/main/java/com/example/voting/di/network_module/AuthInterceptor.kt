package com.example.voting.di.network_module

import android.util.Log
import com.example.domain.use_cases.toke_usecase.GetAdminTokenUseCase
import com.example.domain.use_cases.toke_usecase.GetVoterTokenUseCase
import com.example.domain.utils.Resourse
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject


class AuthInterceptor @Inject constructor():Interceptor {

    private val admin="admin"
    private val voter="voter"
    private var token=""
    private val post="POST"
    private val delete="DELETE"
    private val get="GET"
    @Inject
     lateinit var get_admin_token_usecase: GetAdminTokenUseCase
    @Inject
     lateinit var get_voter_token_usecase: GetVoterTokenUseCase



    override fun intercept(chain: Interceptor.Chain): Response {

        val request_builder = chain.request().newBuilder()
        val request = chain.request()

        runBlocking {

        selectToken(request,post,"candidates",admin)
        selectToken(request,post,"voters",admin)
        selectToken(request,post,"votingTime",admin)
        selectToken(request,get,"candidates/",voter)
        selectToken(request,get,"candidates",voter)
        selectToken(request,get,"voters/me",voter)
        selectToken(request,delete,"candidates/voting",voter)
        selectToken(request,post,"candidates/voting",voter)

        }
        if (token.isNotEmpty())
        request_builder.addHeader("Authorization",  token)
        return chain.proceed(request_builder.build())
    }

    private suspend fun selectToken(request:Request,method:String,end_of_url:String,token_type:String) {
        if (request.url.toString().contains("${end_of_url}") && request.method == method) {

            handleVoterOrAdminToken(token_type)
        }
    }

    private  fun handleGetToken(resourse:Resourse<String>,sucess_handle:()->Unit){

        when (resourse) {
            is Resourse.Loading -> {}
            is Resourse.Success -> sucess_handle()
            is Resourse.Error ->  Log.d("saeed", "${resourse.message} error ")
        }
    }

    private suspend fun handleVoterOrAdminToken(token_type:String){
        if (token_type == admin) {
            val resourse = get_admin_token_usecase()
            handleGetToken(resourse){
                token="bearer ${resourse.data!!}"
            }
        }
        else {
            val resourse = get_voter_token_usecase()
            handleGetToken(resourse){
                token="bearer ${resourse.data!!}"
            }
        }
    }
}

