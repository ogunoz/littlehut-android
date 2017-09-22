package com.valensas.littlehut.busattendance.viewholder

import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import android.widget.TextView
import com.valensas.littlehut.R
import com.valensas.littlehut.busattendance.item.AttendanceListType
import com.valensas.littlehut.busattendance.item.BusAttendanceHeaderViewModel

/**
 * Created by ogun on 21/09/2017.
 * Valensas 2017
 */
class BusAttendanceHeaderViewHolder(parent: ViewGroup) : BusAttendanceViewHolder(parent, R.layout.header_bus_attendance_list) {

    var headerTitleView: TextView
    var headerPartNumView: TextView

    init {
        headerTitleView = itemView.findViewById(R.id.busAttendanceHeaderTitle)
        headerPartNumView = itemView.findViewById(R.id.busAttendancePartSize)
    }

    fun onBindData(viewModel: BusAttendanceHeaderViewModel) {
        headerTitleView.text = viewModel.title
        headerPartNumView.text = viewModel.personNumber.toString()

        when (viewModel.type) {
            AttendanceListType.ATTENDING -> changeHeaderColor(R.color.green)
            AttendanceListType.PENDING -> changeHeaderColor(R.color.gray)
            AttendanceListType.NOT_ATTENDING -> changeHeaderColor(R.color.red)
        }

    }

    fun changeHeaderColor(color: Int) {
        headerTitleView.setTextColor(ContextCompat.getColor(itemView.context, color))
        headerPartNumView.setTextColor(ContextCompat.getColor(itemView.context, color))
    }
}