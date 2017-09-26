package com.valensas.littlehut.busattendance

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.tylerjroach.eventsource.EventSource
import com.tylerjroach.eventsource.MessageEvent
import com.valensas.littlehut.R
import com.valensas.littlehut.core.BasePresenterImpl
import com.valensas.littlehut.network.*
import com.valensas.littlehut.network.sse.SSEHandler

/**
 * Created by ogun on 23/09/2017.
 * Valensas 2017
 */
class BusAttendancePresenter : BusAttendanceContract.Presenter, BasePresenterImpl<BusAttendanceContract.View>() {

    private var isFabOpen = false
    private var eventSource: EventSource? = null
    private var eventHandler: BusSSEHandler? = null

    private var myAttendance: Attendance? = null
    private var busStatus: BusStatus? = null
    private var remainingTime: TimeModel? = null

    override fun attachView(view: BusAttendanceContract.View) {
        super.attachView(view)
        fetchBusAttendanceList()
        getBusAndMyStatus()
        startEventSource()
    }

    override fun detachView() {
        stopEventSource()
        super.detachView()
    }

    override fun onAttendanceStatusButtonClicked() {
        if (isFabOpen) {
            view?.closeFABMenu()
            isFabOpen = false
        } else {
            when (busStatus) {
                BusStatus.busIsWaiting, BusStatus.busHasArrived,
                BusStatus.waitingForBus, BusStatus.bonAppetit -> {
                    view?.showFABMenu()
                    isFabOpen = true
                }
                else -> {
                    Toast.makeText(view?.getContext(), view?.getContext()!!
                            .getString(R.string.check_later), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onAttendanceStatusActionClicked(status: Attendance) {

        if (myAttendance != status) {
            when (busStatus) {
                BusStatus.busIsWaiting, BusStatus.busHasArrived,
                BusStatus.waitingForBus, BusStatus.bonAppetit -> {
                    sendMyAttendance(status)
                }
                else -> Toast.makeText(view?.getContext(), view?.getContext()!!
                        .getString(R.string.check_later), Toast.LENGTH_SHORT).show()
            }
        }
        view?.closeFABMenu()
        isFabOpen = false
    }

    private fun fetchBusAttendanceList() {
        sendRequest(apiService.getBusAttendance()) { response ->
            view?.initAttendanceList(BusAttendanceListViewModel(response))
        }
    }

    private fun getBusAndMyStatus() {
        sendRequest(apiService.getMyAttendance()) { response ->
            myAttendance = response.attendance
            sendRequest(apiService.getBusStatus()) { resp ->
                busStatus = resp.status
                initStatusIcon(myAttendance!!, busStatus!!)
            }
        }
    }

    private fun initStatusIcon(myAttendance: Attendance, busStatus: BusStatus) {
        view?.setFABMenuVisible(View.VISIBLE)
        view?.setFABNotAttendVisible(View.VISIBLE)
        view?.setFABAttendVisible(View.VISIBLE)
        when (myAttendance) {
            Attendance.attending -> {
                view?.changeFABIcon(R.drawable.attending_24, R.color.green)
            }
            Attendance.notAttending -> {
                view?.changeFABIcon(R.drawable.not_attending_24, R.color.red)
            }
            Attendance.pending -> {
                view?.changeFABIcon(R.drawable.pending_24, R.color.colorPrimary)
            }
        }
    }

    private fun sendMyAttendance(status: Attendance) {
        sendRequest(apiService.postMyAttendance(AttendModel(status == Attendance.attending))) { response: BaseResponse ->
            myAttendance = status
            initStatusIcon(myAttendance!!, busStatus!!)
        }
    }

    override fun onContainerClicked() {
        if (isFabOpen) {
            view?.closeFABMenu()
            isFabOpen = false
        }
    }

    private fun setStatusText(remainingTime: TimeModel, busStatus: BusStatus?) {
        if (busStatus != null) {
            val context: Context = view?.getContext()!!
            when (busStatus) {
                BusStatus.waitingForBus -> {
                    view?.setStatusView(context.getString(R.string.waiting_for_bus_title),
                            context.getString(R.string.waiting_for_bus_value, remainingTime.hours, remainingTime.minutes))
                }
                BusStatus.bonAppetit -> {
                    view?.setStatusView("", context.getString(R.string.bon_appetit))
                }
                BusStatus.busHasArrived -> {
                    view?.setStatusView("", context.getString(R.string.bus_has_arrived))
                }
                BusStatus.busIsWaiting -> {
                    view?.setStatusView(context.getString(R.string.bus_is_waiting_title),
                            context.getString(R.string.bus_is_waiting_value, remainingTime.hours, remainingTime.minutes))
                }
                BusStatus.busHasDeparted -> {
                    view?.setStatusView(context.getString(R.string.bus_has_departed_title),
                            context.getString(R.string.bus_has_departed_value))
                }
                BusStatus.busIsReturning -> {
                    view?.setStatusView(context.getString(R.string.bus_is_returning_title),
                            context.getString(R.string.bus_has_returning_value))
                }
                BusStatus.checkTomorrow -> {
                    view?.setStatusView("", context.getString(R.string.check_tomorrow))
                }
                BusStatus.nonWorkingDay -> {
                    view?.setStatusView("", context.getString(R.string.not_working_dat))
                }
                else -> {
                    view?.setStatusView("", context.getString(R.string.check_later))
                }
            }
        }
    }

    private fun startEventSource() {
        eventHandler = BusSSEHandler()
        eventSource = EventSource.Builder(APIService.BASE_URL + "subscribe")
                .headers(APIService.headers)
                .eventHandler(eventHandler)
                .build()
        eventSource?.connect()
    }

    private fun stopEventSource() {
        eventSource?.close()
        eventHandler = null
    }

    inner class BusSSEHandler : SSEHandler() {

        override fun onMessage(event: String?, message: MessageEvent?) {
            super.onMessage(event, message)
            if (message == null) return

            when (event) {
                "userStatusUpdated" -> {
                    val updatedBusAttendance = gson.fromJson(message.data, BusAttendanceModel::class.java)
                    view?.initAttendanceList(BusAttendanceListViewModel(updatedBusAttendance))
                }
                "remainingTimeToBus" -> {
                    remainingTime = gson.fromJson(message.data, TimeModel::class.java)
                    setStatusText(remainingTime!!, busStatus)
                }
                "busStatus" -> {
                    busStatus = BusStatus.valueOf(message.data)
                    setStatusText(remainingTime!!, busStatus)
                }
            }
        }
    }
}