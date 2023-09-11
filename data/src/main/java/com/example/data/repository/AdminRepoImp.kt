package com.example.data.repository

import android.app.Application
import android.util.Log
import com.example.data.mapper.LoginVoterResponseMapper
import com.example.data.remote.api.AdminApi
import com.example.domain.models.Agenda
import com.example.domain.models.LoginVoterResponse
import com.example.domain.repository.AdminRepo
import com.example.domain.utils.Resourse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject


class AdminRepoImp @Inject constructor(private val api:AdminApi):AdminRepo {



    private fun convertToRequestBody( convert: String):RequestBody=convert.toRequestBody("text/plain".toMediaTypeOrNull())



    override suspend fun createVoter(
        id: Long,
        name: String,
        birth_day: String,
        city: String,
        face_id: MultipartBody.Part
    ): Resourse<ResponseBody> {


        val id=convertToRequestBody(id.toString())
        val name=convertToRequestBody(name)
        val birth_day=convertToRequestBody(birth_day)
        val city=convertToRequestBody(city)

        return try {
            val result=api.createVoter(id,name,birth_day,city,face_id)

            if (result.isSuccessful&&result.body()!=null)
                Resourse.Success(result.body())
            else
                Resourse.Error(result.message().toString())
        }catch (e:Exception){
                Resourse.Error(e.message.toString())
        }

    }


    override suspend fun createCandidate(
        name: String,
        code: String,
        date: String,
        political_party: String,
        agenda_list: List<Agenda>,
        image: MultipartBody.Part
    ): Resourse<ResponseBody> {

        val map: MutableMap<String, RequestBody> = mutableMapOf()
       val name=convertToRequestBody(name)
        val code=convertToRequestBody(code)
        val date=convertToRequestBody(date)
        val political_party=convertToRequestBody(political_party)

        map.put("name",name)
        map.put("candidate_code",code)
        map.put("date_of_birth",date)
        map.put("political_party",political_party)

        for((index, agenda) in agenda_list.withIndex()){
            map["agenda_list[${index}][abstraction]"] = convertToRequestBody("${agenda.abstraction}")
            map["agenda_list[${index}][summary]"] = convertToRequestBody("${agenda.summary}")
        }

        try {
            val result=api.createCandidate(map,image)

            if (result.isSuccessful&&result.body()!=null)
                return Resourse.Success(result.body())
            else
                return Resourse.Error(result.message().toString())
        }catch (e:Exception){
            return Resourse.Error(e.message.toString())

        }

    }

    override suspend fun setTime(hours: Int): Resourse<ResponseBody> {
        try {
            val result=api.setTime(hours)

            if (result.isSuccessful&&result.body()!=null)
                return Resourse.Success(result.body())
            else
                return Resourse.Error(result.message().toString())
        }catch (e:Exception){
            return Resourse.Error(e.message.toString())
        }
    }

    override suspend fun loginAdmin(email: String, password: String): Resourse<String>? {

        try {
            val result=api.loginAdmin(email,password)
            if (result.isSuccessful&&result.body()!=null)
                return Resourse.Success(result.body()?.accessToken?:"")
            else
                return Resourse.Error(result.message().toString())
        }catch (e:Exception){
            return Resourse.Error(e.message.toString())

        }
    }
}