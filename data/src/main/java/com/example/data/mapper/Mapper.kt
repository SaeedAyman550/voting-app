package com.example.data.mapper

interface Mapper<i,o> {

    fun map(input:i?):o
}