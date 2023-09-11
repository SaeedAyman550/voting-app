package com.example.data.remote.api

import com.example.data.remote.dto.*
import com.example.domain.utils.Resourse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface AdminApi {

    @Multipart
    @POST("voters")
    suspend fun createVoter(@Part("personal_id") id: RequestBody, @Part("name") name: RequestBody
                            , @Part("date_of_birth") birth_day: RequestBody, @Part("city") city: RequestBody
                            , @Part face_id:MultipartBody.Part):Response<ResponseBody>

    @Multipart
    @POST("candidates")
    suspend fun createCandidate(@PartMap() partMap: MutableMap<String,RequestBody>
                                , @Part image:MultipartBody.Part):Response<ResponseBody>


    @FormUrlEncoded
    @POST("auth/signin")
    suspend fun loginAdmin(@Field("email") email:String,@Field("password") password:String):Response<LoginAdminResponseDto>


    @FormUrlEncoded
    @POST("votingTime")
    suspend fun setTime(@Field("hour_duration")hours:Int):Response<ResponseBody>




}