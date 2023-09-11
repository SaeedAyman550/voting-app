package com.example.domain.repository
import com.example.domain.utils.Resourse
import okhttp3.MultipartBody
import okhttp3.ResponseBody


interface AdminRepo {



    suspend fun createVoter(id:Long, name:String, birth_day:String, city:String,face_id:MultipartBody.Part ): Resourse<ResponseBody>

    suspend fun createCandidate(name: String, code:String, date:String, political_party:String
                                , agenda_list:List<com.example.domain.models.Agenda>, image: MultipartBody.Part): Resourse<ResponseBody>


    suspend fun setTime(hours:Int): Resourse<ResponseBody>




    suspend fun loginAdmin(email:String, password:String):Resourse<String>?



}