package com.example.data.mapper

import com.example.data.remote.dto.AgendaDto
import com.example.domain.models.Agenda

object AgendaMapper:Mapper<AgendaDto?, Agenda> {
    override fun map(input: AgendaDto?): Agenda =

            if (input != null)
                Agenda(input.abstraction ?: "", input.summary ?: "")
            else
                Agenda()

}