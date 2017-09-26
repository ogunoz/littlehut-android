package com.valensas.littlehut.busattendance

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.SpannableString
import android.text.TextUtils
import android.view.View
import com.valensas.littlehut.R
import com.valensas.littlehut.core.BaseFragment
import com.valensas.littlehut.network.Attendance
import com.valensas.littlehut.network.BusStatus
import kotlinx.android.synthetic.main.fragment_bus_attendance.*


/**
 * Created by ogun on 20/09/2017.
 * Valensas 2017
 */
class BusAttendanceFragment : BaseFragment<BusAttendanceContract.View,
        BusAttendanceContract.Presenter>(), BusAttendanceContract.View {

    override var presenter: BusAttendanceContract.Presenter = BusAttendancePresenter()

    private var listAdapter: BusAttendanceListAdapter? = null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_bus_attendance
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        busAttendanceContainerLayout.setOnClickListener {
            presenter.onContainerClicked()
        }
        busAttendanceStatusChangeButton.setOnClickListener {
            presenter.onAttendanceStatusButtonClicked()
        }
        busAttendanceAttendButton.setOnClickListener {
            presenter.onAttendanceStatusActionClicked(Attendance.attending)
        }
        busAttendanceNotAttendButton.setOnClickListener {
            presenter.onAttendanceStatusActionClicked(Attendance.notAttending)
        }
    }

    override fun initAttendanceList(listViewModel: BusAttendanceListViewModel) {
        if (listAdapter == null) {
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            busAttendanceRecyclerView.layoutManager = layoutManager
            listAdapter = BusAttendanceListAdapter(listViewModel)
            busAttendanceRecyclerView.adapter = listAdapter
        } else {
            activity.runOnUiThread({ listAdapter?.updateList(listViewModel) })
        }
    }

    override fun showBusStatus(status: BusStatus) {
        activity.runOnUiThread({ busStatusLargeTextView.text = status.name })
    }

    override fun showFABMenu() {
        busAttendanceAttendButton.animate().translationY(-resources.getDimension(R.dimen.standard_105)).scaleX(1.0f).scaleY(1.0f)
        busAttendanceNotAttendButton.animate().translationY(-resources.getDimension(R.dimen.standard_55)).scaleX(1.0f).scaleY(1.0f)
    }

    override fun closeFABMenu() {
        busAttendanceAttendButton.animate().translationY(0.0f).scaleX(0.4f).scaleY(0.4f)
        busAttendanceNotAttendButton.animate().translationY(0.0f).scaleX(0.4f).scaleY(0.4f)
    }

    override fun changeFABIcon(iconResource: Int, backgroundColor: Int) {
        busAttendanceStatusChangeButton.setImageResource(iconResource)
        busAttendanceStatusChangeButton.backgroundTintList = ColorStateList
                .valueOf(ContextCompat.getColor(context, backgroundColor))
    }

    override fun setFABAttendVisible(visibility: Int) {
        busAttendanceAttendButton.visibility = visibility
    }

    override fun setFABNotAttendVisible(visibility: Int) {
        busAttendanceNotAttendButton.visibility = visibility
    }

    override fun setFABMenuVisible(visibility: Int) {
        busAttendanceStatusChangeButton.visibility = visibility
    }

    override fun setStatusView(smallText: String, largeText: String) {
        activity.runOnUiThread({
            busStatusSmallTextView.visibility = if (TextUtils.isEmpty(smallText)) View.GONE else View.VISIBLE
            busStatusLargeTextView.visibility = if (TextUtils.isEmpty(largeText)) View.GONE else View.VISIBLE
            busStatusSmallTextView.text = smallText
            busStatusLargeTextView.text = largeText
        })
    }

    companion object {
        fun newInstance() = BusAttendanceFragment()
    }
}

