package com.valensas.littlehut.busattendance

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.valensas.littlehut.R
import com.valensas.littlehut.login.LoginActivity
import com.valensas.littlehut.network.APIService
import com.valensas.littlehut.network.AttendModel
import com.valensas.littlehut.network.BusAttendanceModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_bus_attendance.busAttendanceRecyclerView
import kotlinx.android.synthetic.main.fragment_bus_attendance.busAttendanceStatusChangeButton
import kotlinx.android.synthetic.main.fragment_bus_attendance.busAttendanceAttendButton
import kotlinx.android.synthetic.main.fragment_bus_attendance.busAttendanceNotAttendButton
import retrofit2.HttpException


/**
 * Created by ogun on 20/09/2017.
 * Valensas 2017
 */
class BusAttendanceFragment : Fragment() {

    var disposable: Disposable? = null
    var listAdapter: BusAttendanceListAdapter? = null
    var isFabOpen = false

    val apiService by lazy {
        APIService.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bus_attendance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchBusAttendanceList()
        busAttendanceStatusChangeButton.setOnClickListener({
            if (isFabOpen) {
                closeFABMenu()
            } else {
                showFABMenu()
            }
        })
        busAttendanceAttendButton.setOnClickListener({
            sendMyAttendance(true)
            closeFABMenu()
        })
        busAttendanceNotAttendButton.setOnClickListener({
            sendMyAttendance(false)
            closeFABMenu()
        })
    }

    private fun fetchBusAttendanceList() {
        disposable = apiService.getBusAttendance()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> initAttendanceList(result) },
                        { error ->
                            if (error is HttpException && error.code() == 401) {
                                startActivity(LoginActivity.newIntent(context))
                                activity.overridePendingTransition(0, 0)
                                activity.finish()
                            }
                        }
                )
    }

    private fun sendMyAttendance(isAttending: Boolean) {
        disposable = apiService.postMyAttendance(AttendModel(isAttending))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> fetchBusAttendanceList() },
                        { error -> Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show() }
                )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun initAttendanceList(attendanceModel: BusAttendanceModel) {
        val viewModel = BusAttendanceListViewModel(attendanceModel)
        if (listAdapter == null) {
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            busAttendanceRecyclerView.layoutManager = layoutManager
            listAdapter = BusAttendanceListAdapter(viewModel)
            busAttendanceRecyclerView.adapter = listAdapter
            busAttendanceStatusChangeButton.visibility = View.VISIBLE
        } else {
            listAdapter?.updateList(viewModel)
        }
    }

    fun showFABMenu() {
        isFabOpen = true;
        busAttendanceAttendButton.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        busAttendanceNotAttendButton.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
    }

    fun closeFABMenu() {
        isFabOpen = false;
        busAttendanceAttendButton.animate().translationY(0.0f);
        busAttendanceNotAttendButton.animate().translationY(0.0f);
    }

    companion object {

        fun newInstance() = BusAttendanceFragment()
        //  BusAttendanceFragment().apply {
        //       arguments = Bundle().apply { putString(ARGUMENT_TASK_ID, taskId) }
        //    }
    }
}

