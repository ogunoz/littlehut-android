package com.valensas.littlehut.busattendance.item

/**
 * Created by ogun on 21/09/2017.
 * Valensas 2017
 */
data class BusAttendanceHeaderViewModel(val title: String, val personNumber: Int, val type: AttendanceListType) : BusAttendanceViewModel()