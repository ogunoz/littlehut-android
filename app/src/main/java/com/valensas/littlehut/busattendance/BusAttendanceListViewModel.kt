package com.valensas.littlehut.busattendance

import com.valensas.littlehut.busattendance.item.AttendanceListType
import com.valensas.littlehut.busattendance.item.BusAttendanceContentViewModel
import com.valensas.littlehut.busattendance.item.BusAttendanceHeaderViewModel
import com.valensas.littlehut.busattendance.item.BusAttendanceViewModel
import com.valensas.littlehut.network.AttendeeModel
import com.valensas.littlehut.network.BusAttendanceModel

/**
 * Created by ogun on 21/09/2017.
 * Valensas 2017
 */
class BusAttendanceListViewModel(busAttendanceModel: BusAttendanceModel) {

    var busList: ArrayList<BusAttendanceViewModel>

    init {
        busList = ArrayList()
        fillBusListPart(busAttendanceModel.attendingList, "Gelenler Listesi", AttendanceListType.ATTENDING)
        fillBusListPart(busAttendanceModel.pendingList, "Cevap Vermeyenler", AttendanceListType.PENDING)
        fillBusListPart(busAttendanceModel.notAttendingList, "Gelmeyenler Listesi", AttendanceListType.NOT_ATTENDING)
    }

    private fun fillBusListPart(list: ArrayList<AttendeeModel>, subTitle: String, type: AttendanceListType) {
        if (list.size > 0) {
            busList.add(BusAttendanceHeaderViewModel(subTitle, list.size, type))
        }
        for (attendee in list) {
            busList.add(BusAttendanceContentViewModel(attendee.name, attendee.email, attendee.pictureUrl))
        }
    }

}