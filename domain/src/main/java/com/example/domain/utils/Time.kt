package com.example.pressentation.ui.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


 const val lowerTime="lower"
 const val higherTime="higher"

 fun getCurrentTime():String{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return dateFormat.format(Date())
}

 fun compareBetweenTwoDate(dateString1: String, dateString2: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    try {
        val date1 = dateFormat.parse(dateString1)
        val date2 = dateFormat.parse(dateString2)
        if ( date1.compareTo(date2)<0)
            return lowerTime
        else
            return higherTime

    } catch (e: ParseException) {

        return e.message.toString()
    }
}

fun convertFormatDate(dateString:String):String{

    return if (dateString.isNotEmpty()) {
        val inputFormatter = SimpleDateFormat("yyyy-MM-d'T'HH:mm:ss.SSS'Z'")
        val outputFormatter = SimpleDateFormat("yyyy-MM-d")
        val inputDate = inputFormatter.parse(dateString)
        outputFormatter.format(inputDate)
    } else
        dateString
}