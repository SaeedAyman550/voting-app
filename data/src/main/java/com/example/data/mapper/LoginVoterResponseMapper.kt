package com.example.data.mapper

import com.example.data.remote.dto.LoginAdminResponseDto
import com.example.data.remote.dto.LoginVoterResponseDto
import com.example.domain.models.LoginVoterResponse

object LoginVoterResponseMapper:Mapper<LoginVoterResponseDto?, LoginVoterResponse> {
    override fun map(input: LoginVoterResponseDto?): LoginVoterResponse =
        if (input!=null)
            LoginVoterResponse(
                input.accessToken ?: "",
                input.result ?: ""
            )
        else
            LoginVoterResponse()


}