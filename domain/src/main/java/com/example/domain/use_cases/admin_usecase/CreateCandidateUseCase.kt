package com.example.domain.use_cases.admin_usecase


import com.example.domain.repository.AdminRepo
import com.example.domain.utils.Resourse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class CreateCandidateUseCase  @Inject constructor( private val repo: AdminRepo)  {

    private val part_name_image="image"

    suspend operator fun invoke(name: String,
                                code: String,
                                date: String,
                                political_party: String,
                                agenda_list: List<com.example.domain.models.Agenda>
                                , image_file_RealPath: String
    ): Resourse<ResponseBody> {

        return try {
            val image=prepareFilePart(part_name_image,image_file_RealPath)
            repo.createCandidate(name,code,date,political_party,agenda_list,image)

        }catch (e:Exception){

            Resourse.Error("${e.message.toString()}")
        }


    }





    private fun prepareFilePart(partName: String,fileRealPath: String): MultipartBody.Part {
        val file= File(fileRealPath)

        return MultipartBody.Part.createFormData(partName, file.name,  file.asRequestBody("image/jpg".toMediaTypeOrNull()))
    }


}