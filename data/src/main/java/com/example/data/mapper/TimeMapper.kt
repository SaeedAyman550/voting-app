package com.example.data.mapper

import com.example.data.remote.dto.TimeDto
import com.example.domain.models.Time
import com.example.pressentation.ui.utils.convertFormatDate
import java.text.SimpleDateFormat

object TimeMapper:Mapper<TimeDto?, Time> {
    override fun map(input: TimeDto?): Time {
        if (input!=null)
        if (input.result!=null&&input.result.isNotEmpty())
            return Time(
                input.result[input.result.size - 1].hour_duration ?: -1,
                convertFormatDate(
                    input.result[input.result.size - 1].start_at?.get(input.result.size - 1) ?: ""
                ),
                convertFormatDate(
                    input.result[input.result.size - 1].end_at?.get(input.result.size - 1) ?: ""
                ),
                input.result.size
            )
        else
            return Time(0, "", "", 0)
        return Time(0, "", "", 0)
    }




}


