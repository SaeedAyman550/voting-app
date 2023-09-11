package com.example.data.remote.api

import com.example.data.remote.dto.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.Body

interface VoterApi {


    @GET("votingTime")
    suspend fun getTime(): Response<TimeDto>

    @GET("candidates")
    suspend fun getAllCandidate():Response<CandidateMoreDto>

    @GET("candidates/{id}")
    suspend fun getCandidateById(@Path("id")id:String):Response<CandidateOneDto>


    @GET("voters/me")
    suspend fun getMyVoter():Response<VoterDto>

    @FormUrlEncoded
    @POST("candidates/voting/{id_candidate}")
    suspend fun setVoting(@Path("id_candidate")id_candidate:String,@Field("id")id_voter:String):Response<ResponseBody>


    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "candidates/voting/{id_candidate}", hasBody = true)
    suspend fun deleteVoting(@Path("id_candidate")id_candidate:String,@Field("id") id:String):Response<ResponseBody>


    @Multipart
    @POST("FaceIdPython/signin")
    suspend fun loginVoter(@Part("personal_id") id:RequestBody,@Part face_id:MultipartBody.Part):Response<LoginVoterResponseDto>





}