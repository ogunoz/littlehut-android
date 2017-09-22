package com.valensas.littlehut.busattendance

import android.support.annotation.DrawableRes
import com.valensas.littlehut.core.BasePresenter
import com.valensas.littlehut.core.BaseView
import com.valensas.littlehut.network.BusAttendanceModel

/**
 * Created by ogun on 23/09/2017.
 * Valensas 2017
 */
interface BusAttendanceContract {

    interface View : BaseView<Presenter> {
        fun changeFABIcon(@DrawableRes iconResource: Int)
        fun initAttendanceList(list: BusAttendanceModel)
    }

    abstract class Presenter : BasePresenter() {
        abstract fun onAttendanceStatusChangeClicked(status: Boolean)
    }
}