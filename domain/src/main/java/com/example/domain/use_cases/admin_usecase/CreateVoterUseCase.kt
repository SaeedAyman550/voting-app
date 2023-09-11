package com.example.domain.use_cases.admin_usecase


import com.example.domain.repository.AdminRepo
import com.example.domain.utils.Resourse
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Multipart
import java.io.File
import javax.inject.Inject

class CreateVoterUseCase @Inject constructor(private val repo:AdminRepo) {

    private val part_name_face_id_image="faceIdImage"

    suspend operator fun invoke(id: Long, name: String, birth_day: String, city: String
                                , face_id_fileRealPath: String):Resourse<ResponseBody>{

        return try {
            val face_id=prepareFilePart(part_name_face_id_image,face_id_fileRealPath)

            repo.createVoter(id,name,birth_day,city,face_id)


        }catch (e:Exception){

            Resourse.Error("${e.message}")
        }

    }



    private fun prepareFilePart(partName: String,fileRealPath: String): MultipartBody.Part {
         val file= File(fileRealPath)

        return MultipartBody.Part.createFormData(partName, file.name,  file.asRequestBody("image/jpg".toMediaTypeOrNull()))
    }




}