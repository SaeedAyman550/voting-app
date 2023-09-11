package com.example.data.repository


import com.example.data.mapper.CandidateMapper
import com.example.data.mapper.LoginVoterResponseMapper
import com.example.data.mapper.TimeMapper
import com.example.data.mapper.VoterMapper
import com.example.data.remote.api.VoterApi
import com.example.domain.models.Candidate
import com.example.domain.models.LoginVoterResponse
import com.example.domain.models.Time
import com.example.domain.models.Voter
import com.example.domain.repository.VoterRepo
import com.example.domain.utils.Resourse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import javax.inject.Inject

class VoterRepoImp @Inject constructor(private val api:VoterApi):VoterRepo {


    override suspend fun getTime(): Resourse<Time> {
        try {
            val result = api.getTime()
            if (result.isSuccessful&&result.body()!=null)
                return Resourse.Success(TimeMapper.map(result.body()))
            else
                return Resourse.Error(result.message().toString())

        } catch (e: Exception) {

            return Resourse.Error(e.message.toString())
        }
    }

    override suspend fun getAllCandidate(): Resourse<List<Candidate>> {
        try {
            val result=api.getAllCandidate()
            if (result.isSuccessful&&result.body()!=null) {
                val list= mutableListOf<Candidate>()
                result.body()?.result?.onEach {
                    var mapper=CandidateMapper.map(it)
                    list.add(mapper )
                }
                return Resourse.Success(list)
            }
            else
                return Resourse.Error(result.message().toString())
        }catch (e:Exception){

            return Resourse.Error(e.message.toString())
        }
    }

    override suspend fun getCandidateById(id: String): Resourse<Candidate> {

        try {
            val result=api.getCandidateById(id)

            if (result.isSuccessful&&result.body()!=null) {
                val candidate=CandidateMapper.map( result.body()?.result)
                return Resourse.Success(candidate)
            }
            else
                return Resourse.Error(result.message().toString())
        }catch (e:Exception){

            return Resourse.Error(e.message.toString())
        }


    }

    override suspend fun setVoting(id_candidate: String, id_voter: String): Resourse<ResponseBody> {

        try {
            val result=api.setVoting(id_candidate,id_voter)
            if (result.isSuccessful&&result.body()!=null)
                return Resourse.Success(result.body())
            else
                return Resourse.Error(result.message().toString())
        }catch (e:Exception){

            return Resourse.Error(e.message.toString())
        }


    }

    override suspend fun deleteVoting(id_candidate: String, id_voter: String): Resourse<ResponseBody> {

        try {
            val result=api.deleteVoting(id_candidate,id_voter)

            if (result.isSuccessful&&result.body()!=null)
                return Resourse.Success(result.body())
            else
                return Resourse.Error(result.message().toString())

        }catch (e:Exception){

            return Resourse.Error(e.message.toString())
        }

    }



    override suspend fun getMyVoter(): Resourse<Voter> {
        try {
            val result=api.getMyVoter()

            if (result.isSuccessful&&result.body()!=null) {
                val voter = VoterMapper.map(result.body()?.voter)
                return Resourse.Success(voter)
            }else
                return Resourse.Error(result.message().toString())

        }catch (e:Exception){
            return Resourse.Error(e.message.toString())
        }
    }


    private fun convertToRequestBody( convert: String): RequestBody =convert.toRequestBody("text/plain".toMediaTypeOrNull())


    override suspend fun loginVoter(
        id: Long,
        face_id: MultipartBody.Part
    ): Resourse<LoginVoterResponse> {
        try {
            var id=convertToRequestBody(id.toString())
            val result=api.loginVoter(id,face_id)

            if (result.isSuccessful&&result.body()!=null) {
                val login_voter_response=LoginVoterResponseMapper.map(result.body())
                return Resourse.Success(login_voter_response)
            }else
                return Resourse.Error(result.message().toString())

        }catch (e:Exception){
            return Resourse.Error("${e.message.toString()}")
        }
    }




}