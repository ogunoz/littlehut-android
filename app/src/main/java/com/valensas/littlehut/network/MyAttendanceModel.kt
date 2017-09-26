package com.valensas.littlehut.network

import com.google.gson.annotations.SerializedName

/**
 * Created by ogun on 25/09/2017.
 * Valensas 2017
 */
data class MyAttendanceModel(@SerializedName("attendance")
                             val attendance: Attendance) : BaseResponse()