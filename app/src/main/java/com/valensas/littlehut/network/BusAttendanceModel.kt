package com.valensas.littlehut.network

import com.google.gson.annotations.SerializedName

/**
 * Created by ogun on 19/09/2017.
 * Valensas 2017
 */
data class BusAttendanceModel(@SerializedName("attending")
                              val attendingList: ArrayList<AttendeeModel>,
                              @SerializedName("notAttending")
                              val notAttendingList: ArrayList<AttendeeModel>,
                              @SerializedName("pending")
                              val pendingList: ArrayList<AttendeeModel>)