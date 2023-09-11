package com.example.domain.repository


import com.example.domain.utils.Resourse
import okhttp3.MultipartBody
import okhttp3.ResponseBody


interface VoterRepo {


    suspend fun getTime(): Resourse<com.example.domain.models.Time>

    suspend fun getAllCandidate():Resourse<List<com.example.domain.models.Candidate>>

    suspend fun getCandidateById(id:String):Resourse<com.example.domain.models.Candidate>



    suspend fun setVoting(id_candidate:String,id_voter:String):Resourse<ResponseBody>


    suspend fun deleteVoting(id_candidate:String,id_voter:String):Resourse<ResponseBody>

    suspend fun getMyVoter():Resourse<com.example.domain.models.Voter>


    suspend fun loginVoter( id: Long,face_id: MultipartBody.Part):Resourse<com.example.domain.models.LoginVoterResponse>




}