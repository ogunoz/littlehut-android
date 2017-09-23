package com.valensas.littlehut.busattendance

import com.valensas.littlehut.R
import com.valensas.littlehut.core.BasePresenterImpl
import com.valensas.littlehut.network.AttendModel
import com.valensas.littlehut.network.BaseResponse

/**
 * Created by ogun on 23/09/2017.
 * Valensas 2017
 */
class BusAttendancePresenter : BusAttendanceContract.Presenter, BasePresenterImpl<BusAttendanceContract.View>() {

    private var isFabOpen = false

    override fun attachView(view: BusAttendanceContract.View) {
        super.attachView(view)
        fetchBusAttendanceList()
    }

    override fun onAttendanceStatusButtonClicked() {
        if (isFabOpen) {
            view?.closeFABMenu()
        } else {
            view?.showFABMenu()
        }
        isFabOpen = !isFabOpen
    }

    override fun onAttendanceStatusActionClicked(status: Boolean) {
        sendMyAttendance(status)
        view?.closeFABMenu()
    }

    private fun fetchBusAttendanceList() {
        sendRequest(apiService.getBusAttendance()) { response ->
            view?.initAttendanceList(BusAttendanceListViewModel(response))
        }
    }

    private fun sendMyAttendance(isAttending: Boolean) {
        sendRequest(apiService.postMyAttendance(AttendModel(isAttending))) { response: BaseResponse ->
            fetchBusAttendanceList()
            if (isAttending) {
                view?.changeFABIcon(R.drawable.come_24_dp)
            } else {
                view?.changeFABIcon(R.drawable.not_come_24_dp)
            }
        }
    }

    override fun onContainerClicked() {
        if (isFabOpen) {
            view?.closeFABMenu()
        }
    }
}