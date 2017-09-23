package com.valensas.littlehut.busattendance

import android.support.annotation.DrawableRes
import com.valensas.littlehut.core.BasePresenter
import com.valensas.littlehut.core.BaseView

/**
 * Created by ogun on 23/09/2017.
 * Valensas 2017
 */
interface BusAttendanceContract {

    interface View : BaseView {
        fun changeFABIcon(@DrawableRes iconResource: Int)
        fun initAttendanceList(listViewModel: BusAttendanceListViewModel)
        fun showFABMenu()
        fun closeFABMenu()
    }

    interface Presenter : BasePresenter<View> {
        fun onAttendanceStatusActionClicked(status: Boolean)
        fun onAttendanceStatusButtonClicked()
        fun onContainerClicked()
    }
}