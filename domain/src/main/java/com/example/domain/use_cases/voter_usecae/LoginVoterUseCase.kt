package com.example.domain.use_cases.voter_usecae


import com.example.domain.repository.VoterRepo
import com.example.domain.utils.Resourse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class LoginVoterUseCase @Inject constructor(private val repo: VoterRepo){

    private val part_name_face_id="faceIdImageVerification"

    suspend operator fun invoke(personal_id:Long, face_id_fileRealPath: String):Resourse<com.example.domain.models.LoginVoterResponse>{

        val face_id=prepareFilePart(part_name_face_id,face_id_fileRealPath)
        return repo.loginVoter(personal_id,face_id)

    }

    private fun prepareFilePart(partName: String,fileRealPath: String): MultipartBody.Part {
        val file= File(fileRealPath)

        return MultipartBody.Part.createFormData(partName, file.name,  file.asRequestBody("image/jpg".toMediaTypeOrNull()))
    }

}