package com.example.data.mapper

import com.example.data.remote.dto.VoterResultDto
import com.example.domain.models.Voter
import com.example.domain.utils.Constants
import com.example.pressentation.ui.utils.convertFormatDate
import java.text.SimpleDateFormat

object VoterMapper : Mapper<VoterResultDto?, Voter> {

    override fun map(input: VoterResultDto?): Voter {
        if (input != null) {
            var valid_image =
                input.faceIdImage?.replace("localhost:3030", "${Constants.local_host}")
                    ?: ""
            return Voter(
                input._id ?: "",
                if (input.candidate_id == null || input.candidate_id.isEmpty()) ""
                else input.candidate_id.get(0)._id ?: "",
                input.city ?: "",
                convertFormatDate(input.date_of_birth ?: ""),
                valid_image,
                input.isVote ?: false,
                input.name ?: "",
                input.personal_id ?: -1,
                input.refreshToken ?: ""
            )
        } else
            return Voter()
    }


}