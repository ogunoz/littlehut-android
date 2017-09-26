package com.valensas.littlehut.busattendance

import android.support.annotation.DrawableRes
import com.valensas.littlehut.core.BasePresenter
import com.valensas.littlehut.core.BaseView
import com.valensas.littlehut.network.Attendance
import com.valensas.littlehut.network.BusStatus

/**
 * Created by ogun on 23/09/2017.
 * Valensas 2017
 */
interface BusAttendanceContract {

    interface View : BaseView {
        fun changeFABIcon(@DrawableRes iconResource: Int, backgroundColor: Int)
        fun initAttendanceList(listViewModel: BusAttendanceListViewModel)
        fun showBusStatus(status: BusStatus)
        fun showFABMenu()
        fun closeFABMenu()
        fun setFABMenuVisible(visibility: Int)
        fun setFABAttendVisible(visibility: Int)
        fun setFABNotAttendVisible(visibility: Int)
        fun setStatusView(smallText: String, largeText: String)
    }

    interface Presenter : BasePresenter<View> {
        fun onAttendanceStatusActionClicked(status: Attendance)
        fun onAttendanceStatusButtonClicked()
        fun onContainerClicked()
    }
}